<script lang="ts" setup>
import { reactive } from 'vue';
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
    name: '',
    location: {
        country: '',
        city: '',
    },
    date: new Date(),
    type: '',
});
const content = useCrudStore().content;
const error = useCrudStore().error;

function onSubmit() {
    console.log('onSubmit')
    useCrudStore().create(emergency.name, emergency.date, emergency.type, emergency.location.country, emergency.location.city);
    };
</script>

<template>
    <div class="mx-auto">
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <h1 class="px-5 py-8 text-3xl bg-blue-light text-left text-gray-50">Submit new emergency report</h1>

            <form @submit.prevent="onSubmit" class="p-5">
                <div class="relative z-0 w-full mb-6 group">
                    <input type="text" id="name" v-model="emergency.name" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-400 appearance-none focus:outline-none focus:ring-0 focus:border-b-sky-700 peer" placeholder=" " required />
                    <label for="name" class="peer-focus:font-medium absolute text-sm text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-sky-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Title</label>
                </div>
                <div class="grid md:grid-cols-2 md:gap-6">
                    <div class="relative z-0 w-full mb-6 group">
                        <input type="text" id="country" v-model="emergency.location.country" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-400 appearance-none focus:outline-none focus:ring-0 focus:border-sky-700 peer" placeholder=""/>
                        <label for="country" class="peer-focus:font-medium absolute text-sm text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-sky-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Country</label>
                    </div>
                    <div class="relative z-0 w-full mb-6 group">
                        <input type="text" id="city" v-model="emergency.location.city" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-400 appearance-none focus:outline-none focus:ring-0 focus:border-sky-700 peer" placeholder=""/>
                        <label for="city" class="peer-focus:font-medium absolute text-sm text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-sky-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">City</label>
                    </div>
                </div>
                <div class="grid md:grid-cols-2 md:gap-6 mb-8">
                    <div>
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-400">
                            Select emergency type
                        </label>
                            <select id="type" v-model="emergency.type" class="bg-gray-100 border border-gray-400 text-gray-900 text-sm rounded-lg focus:ring-sky-700 focus:border-sky-700 block w-full p-2.5" required>
                                <option disabled value="">Please select one</option>
                                <option v-for="type in EmergencyType" :key="type">
                                    {{type}}
                                </option>
                            </select>
                    </div>
                    <div>
                        <label for="date" class="block mb-2 text-sm font-medium text-gray-400">
                            Select date of emergency
                        </label>
                        <input type="datetime-local" id="date" v-model="emergency.date" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-400 appearance-none focus:outline-none focus:ring-0 focus:border-sky-700 peer" required />
                    </div>
                        
                </div>

                <button type="submit" class="text-white bg-gradient-to-r from-blue-light to-sky-900 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-blue-dark font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2">
                    Submit report
                </button>
            </form>
        </div>

            <div v-show="content === true">

                <div id="popup-modal" tabindex="-1" class="fixed top-1/4 left-1/3 z-50 p-4 overflow-x-hidden overflow-y-auto h-[calc(100%-1rem)] max-h-full">
                    <div class="relative w-full max-w-md max-h-full">
                        <div class="relative bg-gray-700 rounded-lg shadow">
                            <button type="button" @click="useCrudStore().content=false" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center">
                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                <span class="sr-only">Close modal</span>
                            </button>
                            <div class="p-6 text-center">
                                <svg aria-hidden="true" class="mx-auto mb-5 text-lime-400 w-14 h-14" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                                <h3 class="mb-8 text-lg font-normal text-white">
                                    Emergency report submitted successfully
                                </h3>
                                <RouterLink to="/reports/add" custom v-slot="{ navigate }">
                                    <button data-modal-hide="popup-modal" type="button" class="text-white bg-sky-800 hover:bg-sky-600 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2"
                                    @click="useCrudStore().content=false" role="link">
                                        Submit another report
                                    </button>
                                </RouterLink>
                                <RouterLink to="/reports" custom v-slot="{ navigate }">
                                    <button data-modal-hide="popup-modal" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10"
                                    @click="navigate" role="link">
                                        Go to my reports
                                    </button>
                                </RouterLink>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
</template>