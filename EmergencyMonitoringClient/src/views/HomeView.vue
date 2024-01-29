<!-- <script lang="ts" setup>
import { useAuthStore } from '@/stores/auth';
import { onMounted, ref } from 'vue';
const store = useAuthStore();
const token = store.token;
const data = ref('');
onMounted(async () => {
  const response = await fetch('http://localhost:8080/api/dashboard', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  data.value = await response.text();
});
</script> -->

<template>
  <!-- <h1 class="text-3xl mb-8">Home</h1> -->
  <div class="bg-[url('/Main.png')] bg-no-repeat bg-cover bg-center -m-16">
    <div class="absolute top-2/4 ml-32">
      <h1 class="text-5xl text-gray-950 font-light mb-10">From around the world, <br>For your safety</h1>
      <RouterLink to="/tracker" custom v-slot="{ navigate }">
        <button class="my-auto py-2 px-8 border-blue-light border-x border-y bg-gray-100 hover:bg-blue-light hover:border-blue-dark hover:text-white 
          text-gray-950 rounded-3xl" @click="navigate" role="link">
          Track now
        </button>
      </RouterLink>
    </div>
    <img class="invisible" src="/Main.png">
  </div>
  <div class="mt-28 mb-20">
    <h1 class="text-4xl text-gray-950 font-light mb-14">Real-time updates on global <br>emergencies</h1>
    <div class="grid grid-cols-2 gap-28">
      <div>
        <h2 class="text-2xl text-blue-dark font-medium mb-1">Stay informed</h2>
        <p>Monitor the latest updates on any crisis, including natural disasters, terrorist attacks, pandemics, and other emergencies, no matter where it is located.</p>
      </div>
      <div>
        <h2 class="text-2xl text-blue-dark font-medium mb-1">User-friendly</h2>
        <p>Whether you are a first-time user or an experienced contributor, you can quickly and easily share information about emergencies in your area or around the world.</p>
      </div>
      <div>
        <h2 class="text-2xl text-blue-dark font-medium mb-1">Submit and Edit emergency reports</h2>
        <p>Our goal is to create a community-driven platform where people can report emergencies and share information about them. We believe that by coming together and working collaboratively, we can help mitigate the impact of any crisis and save lives.</p>
      </div>
      <div>
        <h2 class="text-2xl text-blue-dark font-medium mb-1">Join our community</h2>
        <p>We encourage you to join our community by submitting your own emergency reports or editing existing ones. Your contributions can make a difference, and by sharing your knowledge and experiences, you can help others stay safe and informed.</p>
      </div>
    </div>
  </div>
  <div class="-mx-16">   
    <img src="/SignupB.png"> 
  </div>
  <div class="grid grid-cols-2 gap-64 my-20 bg-[url('/Line.png')] bg-no-repeat bg-center">
      <div>
        <p class="text-2xl text-gray-950 font-light">By working together, we can build a stronger and more resilient community that is better equipped to deal with any emergency. </p>
      </div>
      <div>
        <p class="text-2xl text-gray-950 font-light mb-6">Join us today and be part of the solution!</p>  
        <RouterLink to="/login" custom v-slot="{ navigate }">
        <button class="ml-32 my-auto py-2 px-8 text-white border-blue-light border-x border-y bg-blue-dark hover:bg-blue-light hover:border-blue-dark
          rounded-3xl" @click="navigate" role="link">
          Join Today
        </button>
        </RouterLink>
      </div>
    </div>
</template>