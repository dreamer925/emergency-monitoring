<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
const user = reactive({
  name: '',
  username: '',
  password: '',
});

function onSubmit() {
  console.log(user);
  if (user.name != '' && user.username != '' && user.password != '') {
    useAuthStore().signup(user.name, user.username, user.password);
  }
}
</script>

<template>

  <form class="bg-white mx-auto w-1/4 p-8 rounded-md shadow-md" @submit.prevent="onSubmit">
    <h1 class="text-3xl text-center mb-6">Sign Up</h1>
    <div class="mb-4">
      <label for="name" class="block mb-2 text-sm font-semibold text-gray-800">
        Name
      </label>
      <div class="relative">
        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
          <svg aria-hidden="true" class="w-5 h-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path><path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path></svg>
        </div>
        <input type="text" id="name" v-model="user.name" class="bg-stone-50 border border-stone-300 text-gray-900 text-sm rounded-lg focus:ring-sky-600 focus:border-sky-600 block w-full pl-10 p-2.5" placeholder="name">
      </div>

      <label for="username" class="block mb-2 text-sm font-semibold text-gray-800">
        Email
      </label>
      <div class="relative">
        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
          <svg aria-hidden="true" class="w-5 h-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path><path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path></svg>
        </div>
        <input type="email" id="username" v-model="user.username" class="bg-stone-50 border border-stone-300 text-gray-900 text-sm rounded-lg focus:ring-sky-600 focus:border-sky-600 block w-full pl-10 p-2.5" placeholder="name@mail.com">
      </div>

      <label for="password" class="block mb-2 text-sm font-semibold text-gray-800">
        Password
      </label>
      <div class="relative">
        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
          <svg aria-hidden="true" class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M16.5 10.5V6.75a4.5 4.5 0 10-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 002.25-2.25v-6.75a2.25 2.25 0 00-2.25-2.25H6.75a2.25 2.25 0 00-2.25 2.25v6.75a2.25 2.25 0 002.25 2.25z" stroke-linecap="round" stroke-linejoin="round"></path>
          </svg>
        </div>
        <input type="password" id="password" v-model="user.password" class="bg-stone-50 border border-stone-300 text-gray-900 text-sm rounded-lg focus:ring-sky-600 focus:border-sky-600 block w-full pl-10 p-2.5" placeholder="password">
      </div>
        
    </div> 
    <div class="flex items">
      <button class="mx-auto my-auto py-2 px-8 text-white border-blue-light border-x border-y bg-sky-700 hover:bg-sky-600 hover:border-blue-dark
          rounded-3xl" type="submit">
          Sign Up
        </button>
    </div>
  </form>
</template>