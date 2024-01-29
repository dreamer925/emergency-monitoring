<script lang="ts" setup>
import { onMounted, ref, reactive, computed } from 'vue';
import { HttpStatusCode } from 'axios'
import { useNotificationStore } from '@/stores/notification'
import moment from 'moment'

const notify = useNotificationStore();
const searchQuery = ref('');

async function sendRequest () {
    const q = searchQuery.value;
    console.log(q);
    const response = await fetch('http://localhost:8080/api/emergencies/filter?name=' + q);

    if (response.status === 200) {

        const res  = await response.json();

        emergencies.value = res;

        console.log(res);
        console.log(emergencies);
    
    } else if (response.status === HttpStatusCode.BadRequest) {
        console.log('emSearch BadRequest');
        const errorMessage = await response.text();
        notify.setError(errorMessage);
    } else {
        console.log('emSearch error');
        const errorMessage = await response.text();
        notify.setError(errorMessage);
        // throw new Error(t);
    }

};

// const sortedEmergencies = computed((sort) => {
//     if (sort === 'name'){
//         return emergencies.value.sort((a,b) => {
//             let fa = a.name.toLowerCase(), fb = b.name.toLowerCase();
//                 if (fa < fb) {
//                     return -1
//                 }
//                 if (fa > fb) {
//                     return 1
//                 }
//                 return 0
//         })
//     } else if (sort === '') {
//         return emergencies.value.sort((a,b) => {
//             let fa = a.location..toLowerCase(), fb = b.name.toLowerCase();
//                 if (fa < fb) {
//                     return -1
//                 }
//                 if (fa > fb) {
//                     return 1
//                 }
//                 return 0
//         })
//     }
// })

const emergency = reactive({
    id: 1 as number,  
    name: '' as string,
    location: {
        country: '' as string,
        city: '' as string,
    },
    date: new Date(),
    type: '' as string,
});

const emergencies = ref(Array(emergency));

onMounted(async () => {
      const response = await fetch('http://localhost:8080/api/emergencies/tracker');

      if (response.status === 200) {

        const res  = await response.json();

        emergencies.value = res;

        console.log(res);
        console.log(emergencies);
        
      }  else {
        console.log('emTrack error');
        const errorMessage = await response.text();
        notify.setError(errorMessage);
        // throw new Error(t);
      }

    });

// onMounted(async () => {
//     axios.get('http://localhost:8080/api/emergencies/tracker').then((response) => {
//         emergencies.value = response.data
//         // emergencies = toRaw(data.value)
//         console.log(response.data)
//         console.log(emergencies)
//     }).catch(function (response) {
//                     console.log(response)
//                 });
// });
</script>

<template>
    <div class="mx-auto">
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left text-gray-400">
                <caption class="p-5 text-3xl font-semibold text-left text-white bg-blue-light">
                    Emergencies
                    <p class="mt-4 text-sm font-normal text-gray-100">
                        This is the page where you can review, edit and remove your emergency reports.
                    </p>
                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <svg class="w-6 h-6 text-gray-400" aria-hidden="true" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                <path d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" stroke-linecap="round" stroke-linejoin="round"></path>
                            </svg>
                        </div>
                        <input type="search" v-model="searchQuery" @keyup.enter="sendRequest()" class="bg-gray-50 pl-10 mt-6 border border-blue-light text-gray-950 text-base font-thin rounded-3xl focus:ring-slate-300 focus:border-slate-300 block w-full p-2" placeholder="Search for emergencies..">
                    </div>
                </caption>
                <thead class="text-xs uppercase bg-blue-dark text-blue-300">
                    <tr>
                        <th scope="col" class="px-6 py-3">
                            Name
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Location
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Type
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Date
                        </th>
                    </tr>
                </thead>

                <tbody>
                    <tr class="odd:bg-slate-100 even:bg-slate-300 hover:bg-slate-400 border-b text-gray-950 text-base"
                     v-for="emergency in emergencies" v-bind:key="emergency.id">
                        <td scope="row" class="px-6 py-4">
                            {{emergency.name.charAt(0).toUpperCase() + emergency.name.slice(1)}}
                        </td>
                        <td class="px-6 py-4" v-if="!emergency.location.country && !emergency.location.city">
                          Not specified
                        </td>
                        <td class="px-6 py-4" v-else-if="!emergency.location.country">
                            {{emergency.location.city}}
                        </td>
                        <td class="px-6 py-4" v-else-if="!emergency.location.city">
                            {{emergency.location.country}}
                        </td>
                        <td class="px-6 py-4" v-else>
                            {{emergency.location.country}}, {{emergency.location.city}}
                        </td>
                        <td class="px-6 py-4" >
                            {{emergency.type}}
                        </td>
                        <td class="px-6 py-4" >
                            {{moment(String(emergency.date)).format('MM/DD/YYYY hh:mm')}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>