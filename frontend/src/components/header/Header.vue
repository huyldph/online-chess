<template>
  <header class="bg-gray-600 px-4 py-3 shadow-md">
    <div class="max-w-7xl mx-auto flex items-center justify-between">
      <!-- Logo -->
      <div class="flex items-center space-x-2">
        <img src="/logo.jpg" alt="Logo" class="w-10 h-10 rounded-full object-cover" />
        <span class="text-white font-semibold text-lg hidden sm:inline">Chess Online</span>
      </div>

      <!-- Search Bar -->
      <div class="flex-1 mx-6 hidden sm:block">
        <input
            v-model="searchQuery"
            type="text"
            placeholder="Tìm kiếm..."
            class="w-full bg-gray-500 text-white rounded-full px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>
    </div>
  </header>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { logOut } from "@/services/authenticationService";

export default {
  setup() {
    const router = useRouter();
    const searchQuery = ref("");
    const messageCount = ref(2);
    const notificationCount = ref(4);
    const menuVisible = ref(false);

    const goToProfile = () => {
      menuVisible.value = false;
      router.push("/profile");
    };

    const goToSettings = () => {
      menuVisible.value = false;
      router.push("/settings");
    };

    const handleLogout = () => {
      menuVisible.value = false;
      logOut();
      router.push("/login");
    };

    return {
      searchQuery,
      messageCount,
      notificationCount,
      menuVisible,
      goToProfile,
      goToSettings,
      handleLogout,
    };
  },
};
</script>

<style scoped>
.badge {
  @apply absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center;
}

.dropdown-item {
  @apply px-4 py-3 hover:bg-gray-100 cursor-pointer text-sm;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
