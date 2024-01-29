<script setup lang="ts">
import { reactive } from 'vue';
import { useEditStore } from '@/stores/edit';

const editStore = useEditStore();

import { useCrudStore } from '@/stores/crud';

const EmergencyType = [
    'Blizzards', 'Chemical_spills', 'Dam_failure',
    'Droughts', 'Earthquake',
    'Extreme_heat_waves', 'Fire',
    'Floods', 'Hurricanes',
    'Pandemics', 'Terrorist_attacks',
    'Thunder_storms', 'Tornadoes',
    'Other'
];

const emergency = reactive({
    id: editStore.emergency.id ? editStore.emergency.id : 1,
    name: editStore.emergency.name ? editStore.emergency.name : '',
    location: {
        country: editStore.emergency.location.country ? editStore.emergency.location.country : '',
        city: editStore.emergency.location.city ? editStore.emergency.location.city : '',
    },
    date: editStore.emergency.date ? editStore.emergency.date : new Date(),
    type: editStore.emergency.type ? editStore.emergency.type : '',
});

function onSubmit() {
    console.log('onSubmit')
    useCrudStore().update(emergency.id, emergency.name, emergency.date, emergency.type, emergency.location.country, emergency.location.city);
};

</script>

<template>
    <!-- <div v-if="errorStore.error != null" :class="['error-alert', { 'fade-slide-enter': errorStore.error != null, 'fade-slide-leave': !errorStore.error }]" key="alert"
    :style="{
             'animation-duration': '1s',
             'transition': 'opacity 0.5s, transform 0.5s'
           }"
    id="toast-danger" class="flex items-center w-full max-w-xs p-4 mb-4 rounded-lg shadow text-gray-400 bg-slate-800" role="alert"> -->
      <!-- <div class="absolute z-20 mx-auto shadow-2xl rounded-lg w-full max-w-2xl h-full md:h-auto"> -->
    <div class="absolute z-20 mx-auto top-auto mt-0.5 left-1/4 shadow-2xl rounded-lg w-full max-w-2xl h-full md:h-auto">
        <!-- Modal content -->
        <div class="relative p-4 bg-slate-200 rounded-lg shadow-lg sm:p-5">
            <!-- Modal header -->
            <div class="flex justify-between items-center pb-2 mb-2 rounded-t border-b sm:mb-4">
                <h3 class="text-lg font-semibold text-gray-700">
                    Update an Emergency Report
                </h3>
                <button type="button" @click="editStore.finishEditing()" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="updateProductModal">
                    <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                    <span class="sr-only">Close modal</span>
                </button>
            </div>
            <!-- Modal body -->
            <form @submit.prevent="onSubmit">
                <div>
                    <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Title</label>
                    <input type="text" id="name" v-model="emergency.name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" :placeholder="emergency.name">
                </div>
                <div class="grid gap-4 mb-4 sm:grid-cols-2 mt-4">
                    <div>
                        <label for="country" class="block mb-2 text-sm font-medium text-gray-900">Country</label>
                        <input type="text" id="country" v-model="emergency.location.country" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" :placeholder="emergency.location.country">
                    </div>
                    <div>
                        <label for="city" class="block mb-2 text-sm font-medium text-gray-900">City</label>
                        <input type="text" id="city" v-model="emergency.location.city" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" :placeholder="emergency.location.city">
                    </div>
                    <div>
                        <label for="date" class="block mb-2 text-sm font-medium text-gray-900">Date</label>
                        <input type="datetime-local" id="date" v-model="emergency.date" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5">
                    </div>
                    <div>
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
                        <select id="type" v-model="emergency.type" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5" :placeholder="emergency.type">
                            <!-- <option selected="">Electronics</option> -->
                            <option v-for="type in EmergencyType" :key="type">
                                {{type}}
                            </option>
                        </select>
                    </div>
                </div>
                <div class="flex items-center space-x-4 mt-7">
                    <button type="submit" class="text-white bg-blue-light hover:bg-blue-dark focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                        Update a report
                    </button>
                    <button type="button" @click="editStore.finishEditing()" class="text-red-600 bg-gray-50 inline-flex items-center hover:text-white border border-red-600 hover:bg-red-400 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                        Discard
                    </button>
                </div>
            </form>
        </div>
      </div>
</template>@/stores/notification