<template>
  <div>
    <Header />

    <div v-if="userDetails">
      <div class="flex flex-col items-center justify-center h-screen bg-gray-100">
        <div class="p-6 shadow-lg rounded-lg max-w-md w-full bg-white">
          <div class="flex flex-col items-center space-y-4">
            <img
                :src="userDetails.avatar || 'https://ui-avatars.com/api/?name=' + encodeURIComponent(userDetails.username)"
                :alt="`${displayName}'s profile`"
                class="w-24 h-24 rounded-full object-cover"
            />
            <p class="text-gray-700">Welcome back to online chess,</p>
            <h1 class="text-2xl font-bold text-gray-900">{{ displayName }}</h1>
            <p class="text-sm text-gray-500">{{ userDetails.username }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Nếu chưa có thông tin (đang loading) -->
    <div v-else>
      <div class="flex flex-col items-center justify-center gap-8 h-screen">
        <!-- Spinner Tailwind -->
        <div class="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
        <p class="text-gray-600">Loading...</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import Header from './header/Header.vue'
import { getToken } from '../services/localStorageService'

const router = useRouter()
const userDetails = ref(null)

// Tính toán tên hiển thị từ dữ liệu có sẵn
const displayName = computed(() => {
  if (!userDetails.value) return ''
  
  if (userDetails.value.fullName) {
    return userDetails.value.fullName
  }
  
  if (userDetails.value.firstName && userDetails.value.lastName) {
    return `${userDetails.value.firstName} ${userDetails.value.lastName}`
  }
  
  if (userDetails.value.firstName) {
    return userDetails.value.firstName
  }
  
  if (userDetails.value.lastName) {
    return userDetails.value.lastName
  }
  
  // Trường hợp không có tên, hiển thị username
  return userDetails.value.username
})

const getUserDetails = async (token) => {
  try {
    const response = await fetch('http://localhost:8080/identity/users/my-info', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
    
    if (!response.ok) {
      throw new Error('Failed to fetch user details')
    }
    
    const data = await response.json()
    userDetails.value = data.result
    console.log('User details:', data.result)
  } catch (error) {
    console.error('Error fetching user details:', error)
    await router.push('/login')
  }
}

onMounted(() => {
  const token = getToken()

  if (!token) {
    router.push('/login')
    return
  }

  getUserDetails(token)
})
</script>
