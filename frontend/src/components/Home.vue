<template>
  <div>
    <Header />

    <div v-if="userDetails">
      <div class="flex flex-col items-center justify-center h-screen bg-gray-100">
        <div class="p-6 shadow-lg rounded-lg max-w-md w-full bg-white">
          <div class="flex flex-col items-center space-y-4">
            <img
                :src="userDetails.picture"
                :alt="`${userDetails.given_name}'s profile`"
                class="w-24 h-24 rounded-full object-cover"
            />
            <p class="text-gray-700">Welcome back to online chess,</p>
            <h1 class="text-2xl font-bold text-gray-900">{{ userDetails.name }}</h1>
            <p class="text-sm text-gray-500">{{ userDetails.email }}</p>
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Header from './header/Header.vue'
import { getToken } from '../services/localStorageService'

const router = useRouter()
const userDetails = ref(null)

const getUserDetails = async (accessToken) => {
  const response = await fetch(
      `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${accessToken}`
  )
  const data = await response.json()
  userDetails.value = data
}

onMounted(() => {
  const accessToken = getToken()

  if (!accessToken) {
    router.push('/login')
  }

  getUserDetails(accessToken)
})
</script>
