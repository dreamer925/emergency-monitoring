<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { reactive, ref } from 'vue';
import Notifications from './components/Notifications.vue'

const auth = useAuthStore();

function logout() {
  auth.logout();
}

const showDiv = ref(false);

function onHover() {
  showDiv.value = !showDiv.value;
}
</script>

<template>
  <!-- <div class="min-h-screen scroll-smooth bg-gray-100"> -->
  <div class=" scroll-smooth bg-gray-100">
    <header class="p-7">
      <nav class="fixed top-0 left-0 right-0 flex flex-row list-none p-2 bg-white z-20">
        <a href="/"><img class="scale-75 ml-4" alt="Emergency Monitoring" src="/Logo.png"></a>
        <RouterLink to="/" class="ml-4 my-auto">Home</RouterLink>
        <RouterLink to="/tracker" class="ml-4 my-auto">Tracker</RouterLink>
        <RouterLink to="/about" class="ml-4 my-auto">About</RouterLink>
        <RouterLink to="/login" custom v-slot="{ navigate }" v-if="!auth.user">
          <button class="absolute right-0 my-auto mx-10 py-1 px-8 border-blue-light border-x border-y bg-gray-50 hover:bg-blue-light hover:border-blue-dark hover:text-white 
          text-gray-950 rounded-3xl" @click="navigate" role="link">
            Login
          </button>
        </RouterLink>

        <div v-else class="absolute right-0 my-auto">
          <RouterLink to="/reports/add" custom v-slot="{ navigate }">
            <button class="py-1 px-8 border-blue-light border-x border-y bg-gray-50 hover:bg-blue-light hover:border-blue-dark hover:text-white 
            text-gray-950 rounded-3xl" @click="navigate" role="link">
              Report
              <img class="inline-flex items-center w-5 h-5 ml-1" src="/pencil-square.png">
            </button>
          </RouterLink>
          <button v-on:mouseenter="onHover" v-on:mouseleave="onHover" class="mx-10 py-1 px-12 border-blue-light border-x border-y hover:bg-blue-light hover:border-blue-dark hover:text-white 
          text-gray-950 rounded-2xl" type="button">
          Hi, {{auth.name}} 
            <svg class="inline-flex items-center w-4 h-4 ml-2" aria-hidden="true" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
            </svg>
            <!-- Dropdown menu -->
            <div v-show="showDiv" class="z-10 absolute top-8 right-11 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                <ul class="py-2 text-sm text-gray-700 dark:text-gray-200">
                  <li>
                    <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      Settings
                    </a>
                  </li>
                  <li v-show="auth.role=='admin'">
                    <a href="/moderation" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      Moderation
                    </a>
                  </li>
                  <li>
                    <a href="/reports" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      My reports
                    </a>
                  </li>
                </ul>
                <div class="py-2">
                  <a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white" 
                  @click="logout">
                    Sign out
                  </a>
                </div>
            </div>
          </button>
        </div>
          <!-- <button v-on:mouseenter="onHover" v-on:mouseleave="onHover" class="absolute right-0 my-auto mx-10 py-1 px-12 border-blue-light border-x border-y bg-gray-50 hover:bg-blue-light hover:border-blue-dark hover:text-white 
          text-gray-950 rounded-2xl" type="button" v-else>
          Hi, {{auth.name}} 
            <svg class="inline-flex items-center w-4 h-4 ml-2" aria-hidden="true" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
            </svg> -->
            <!-- Dropdown menu -->
            <!-- <div v-show="showDiv" class="z-10 absolute top-8 right-0.5 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                <ul class="py-2 text-sm text-gray-700 dark:text-gray-200">
                  <li>
                    <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      Settings
                    </a>
                  </li>
                  <li v-show="auth.role=='admin'">
                    <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      Moderation
                    </a>
                  </li>
                  <li>
                    <a href="/reports" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      My reports
                    </a>
                  </li>
                </ul>
                <div class="py-2">
                  <a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white" 
                  @click="logout">
                    Sign out
                  </a>
                </div>
            </div>
          </button> -->
      </nav>
    </header>
    <!-- <main class="p-16 min-h-screen">
      <Notifications class="fixed top-16 left-3/4 mx-auto z-10"/>
      
      <RouterView />
    </main> -->
    <main class="p-16 min-h-screen">
      <Notifications class="fixed top-32 left-3/4 mx-auto z-10"/>
      <RouterView />
    </main>
    <footer>
      <div class="bg-blue-light bottom-0 h-36 text-gray-50 text-center">
      <p class="pt-14">Made by Mary A</p>
    </div>
    </footer>
    <!-- <div class="bg-blue-light bottom-0  bo h-36 text-gray-50 text-center">
      <p class="pt-14">Made by Mary A</p>
    </div> -->
  </div>
</template>
