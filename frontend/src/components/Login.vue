<template>
  <div class="flex flex-col items-center justify-center min-h-screen bg-gray-100">
    <div class="bg-white shadow-lg rounded-2xl p-8 w-full max-w-sm">
      <h1 class="text-2xl font-semibold text-center mb-6">Welcome to us</h1>
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <input
            v-model="username"
            type="text"
            placeholder="Username"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
            v-model="password"
            type="password"
            placeholder="Password"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button
            type="submit"
            class="w-full py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition"
        >
          Login
        </button>
      </form>

      <div class="mt-6 flex flex-col gap-4">
        <!-- Google Login -->
        <button
            @click="handleGoogle"
            class="flex items-center justify-center gap-2 w-full py-2 bg-gray-100 text-gray-800 border border-gray-300 rounded-md hover:bg-gray-200 transition"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
            <path
                d="M21.35 11.1H12v2.9h5.4c-.3 1.45-1.15 2.55-2.45 3.3v2.75h3.95c2.3-2.1 3.6-5.2 3.6-8.95 0-.65-.05-1.3-.15-1.9z"
                fill="#4285F4"
            />
            <path
                d="M12 22c2.7 0 4.95-.9 6.6-2.45l-3.95-2.75c-1.1.75-2.5 1.15-4.05 1.15-3.1 0-5.75-2.1-6.7-4.95H.85v3.1C2.5 19.95 6.85 22 12 22z"
                fill="#34A853"
            />
            <path
                d="M5.3 13c-.25-.75-.4-1.55-.4-2.4s.15-1.65.4-2.4V5.1H.85C.3 6.25 0 7.6 0 9s.3 2.75.85 3.9L5.3 13z"
                fill="#FBBC05"
            />
            <path
                d="M12 4.8c1.45 0 2.75.5 3.8 1.5l2.85-2.85C16.95 1.5 14.7.5 12 .5 6.85.5 2.5 2.55.85 5.1L5.3 7.2C6.25 5.25 8.9 3.3 12 4.8z"
                fill="#EA4335"
            />
          </svg>
          Continue with Google
        </button>

        <!-- Facebook Login -->
        <button
            @click="handleFacebook"
            class="flex items-center justify-center gap-2 w-full py-2 bg-blue-600 text-white border border-gray-300 rounded-md hover:bg-blue-700 transition"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
            <path
                d="M12 2.04c5.52 0 10 4.48 10 10 0 5.14-3.84 9.48-8.86 9.95v-7.06h2.74l.41-3.23h-3.14v-2.06c0-.94.26-1.58 1.57-1.58h1.68V6.9h-2.9c-3.57 0-5.24 1.93-5.24 5.21v3.09h-3.47v3.23h3.47v7.06C3.84 21.48 0 17.14 0 12.04 0 6.48 4.48 2.04 12 2.04z"
            />
          </svg>
          Continue with Facebook
        </button>

        <div class="border-t border-gray-300"></div>

        <button
            @click="goToRegister"
            class="w-full py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition"
        >
          Create an account
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getToken } from "@/services/localStorageService";
import { OAuthConfig } from "@/configurations/configuration";

const router = useRouter();
const username = ref("");
const password = ref("");

const handleSubmit = () => {
  console.log("Username:", username.value);
  console.log("Password:", password.value);
};

const handleGoogle = () => {
  const callbackUrl = OAuthConfig.redirectUri;
  const authUrl = OAuthConfig.authUri;
  const googleClientId = OAuthConfig.clientId;

  const targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
      callbackUrl
  )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

  console.log(targetUrl);
  window.location.href = targetUrl;
};

const handleFacebook = () => {
  const facebookAppId = OAuthConfig.facebookAppId;
  const callbackUrl = OAuthConfig.redirectUri;

  const targetUrl = `https://www.facebook.com/v12.0/dialog/oauth?client_id=${facebookAppId}&redirect_uri=${encodeURIComponent(
      callbackUrl
  )}&response_type=code&scope=email,public_profile`;

  console.log(targetUrl);
  window.location.href = targetUrl;
};

const goToRegister = () => {
  router.push("/register"); // Navigate to the register page
};

onMounted(() => {
  const token = getToken();
  if (token) {
    router.push("/"); // Redirect to home if already logged in
  }
});
</script>
