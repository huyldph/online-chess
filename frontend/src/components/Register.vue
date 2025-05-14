<template>
  <div class="min-h-screen bg-black flex flex-col justify-center py-12 sm:px-6 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-md">
      <h2 class="mt-6 text-center text-3xl font-extrabold text-yellow-400">
        Welcome to Us
      </h2>
      <p class="mt-2 text-center text-sm text-yellow-600">
        Or
        <router-link to="/login" class="font-medium text-yellow-300 hover:text-yellow-400">
          sign in to your account
        </router-link>
      </p>
    </div>

    <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-[#0e1424] py-8 px-4 shadow sm:rounded-lg sm:px-10 text-yellow-200">
        <form class="space-y-6" @submit.prevent="handleSubmit">
          <div>
            <label for="name" class="block text-sm font-medium text-yellow-300">
              Full Name
            </label>
            <div class="mt-1">
              <input id="name" name="name" type="text" required
                     class="block w-full px-3 py-2 border border-yellow-600 bg-black text-yellow-100 rounded-md placeholder-yellow-500 focus:outline-none focus:ring-yellow-500 focus:border-yellow-500 sm:text-sm"
                     v-model="name" />
            </div>
          </div>

          <div>
            <label for="email" class="block text-sm font-medium text-yellow-300">
              Email address
            </label>
            <div class="mt-1">
              <input id="email" name="email" type="email" required
                     class="block w-full px-3 py-2 border border-yellow-600 bg-black text-yellow-100 rounded-md placeholder-yellow-500 focus:outline-none focus:ring-yellow-500 focus:border-yellow-500 sm:text-sm"
                     v-model="email" />
            </div>
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-yellow-300">
              Password
            </label>
            <div class="mt-1">
              <input id="password" name="password" type="password" required
                     class="block w-full px-3 py-2 border border-yellow-600 bg-black text-yellow-100 rounded-md placeholder-yellow-500 focus:outline-none focus:ring-yellow-500 focus:border-yellow-500 sm:text-sm"
                     v-model="password" />
            </div>
          </div>

          <div>
            <label for="confirmPassword" class="block text-sm font-medium text-yellow-300">
              Confirm Password
            </label>
            <div class="mt-1">
              <input id="confirmPassword" name="confirmPassword" type="password" required
                     class="block w-full px-3 py-2 border border-yellow-600 bg-black text-yellow-100 rounded-md placeholder-yellow-500 focus:outline-none focus:ring-yellow-500 focus:border-yellow-500 sm:text-sm"
                     v-model="confirmPassword" />
            </div>
          </div>

          <div class="flex items-center">
            <input id="terms" name="terms" type="checkbox" required
                   class="h-4 w-4 text-yellow-500 focus:ring-yellow-500 border-yellow-600 rounded"
                   v-model="acceptTerms" />
            <label for="terms" class="ml-2 block text-sm text-yellow-200">
              I agree to the
              <a href="#" class="font-medium text-yellow-400 hover:text-yellow-300">
                Terms and Conditions
              </a>
            </label>
          </div>

          <div class="space-y-3">
            <button type="submit"
                    class="w-full flex justify-center py-2 px-4 rounded-md text-sm font-medium text-black bg-yellow-400 hover:bg-yellow-500 focus:outline-none focus:ring-2 focus:ring-yellow-500">
              Register
            </button>
            <button type="button"
                    class="w-full flex justify-center py-2 px-4 border border-yellow-400 text-yellow-400 rounded-md text-sm font-medium hover:bg-yellow-500 hover:text-black transition">
              Continue with Google
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
