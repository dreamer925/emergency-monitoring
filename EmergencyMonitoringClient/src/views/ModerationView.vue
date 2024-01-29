<script lang="ts" setup>
import { useAuthStore } from '@/stores/auth';
import { onMounted, ref } from 'vue';
import { reactive } from 'vue';
import moment from 'moment';
import { HttpStatusCode } from 'axios';
import EditReports from '@/components/EditReports.vue';
import DeleteReports from '@/components/DeleteReports.vue';
import { useEditStore } from '@/stores/edit';
import { useDeleteStore } from '@/stores/delete';
import { useNotificationStore } from '@/stores/notification'

const auth = useAuthStore();
const token = auth.token;
const content = ref(false);
const notify = useNotificationStore();

const edit = useEditStore();
const del = useDeleteStore();

const emergency = reactive({
  id: 1,  
  name: '',
    location: {
        country: '',
        city: '',
    },
    date: new Date(),
    type: '',
});

const emergencies = ref(Array(emergency));

onMounted(async () => {
      const response = await fetch('http://localhost:8080/api/emergencies/tracker', {
        // method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
      });

      if (response.status === 200) {

        const res  = await response.json();

        content.value = true;

        emergencies.value = res;

        console.log(res);
        console.log(emergencies);
        
      } else if (response.status === HttpStatusCode.NoContent) {
        console.log('NoContent')
        content.value = false;
      } else {
        console.log('emReports error');
        const errorMessage = await response.text();
        notify.setError(errorMessage);
        // const t = await response.text();
        // throw new Error(t);
      }

    });
</script>

<template>
  <delete-reports v-if="del.state == 'on'"/>
  <edit-reports v-if="edit.state == 'on'"/>
    <div class="mx-auto ">
      <!-- <div class="mx-auto, absolute z-30"><edit-reports /></div> -->
      
      <div v-if="content" class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left text-gray-400">
                    <caption class="p-5 text-3xl font-semibold text-left text-white bg-blue-light">
                        Moderation
                      <p class="mt-4 text-sm font-normal text-gray-100">
                        This is the page where you can review, edit and remove all the reports.
                      </p>
                  </caption>
                    <thead class="text-xs uppercase bg-blue-dark text-blue-300">
                      <tr>
                        <th scope="col" class="px-6 py-3">
                          Title
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
                        <th scope="col" class="px-6 py-3">
                          Action
                        </th>
                      </tr>
                    </thead>

                    <tbody>
                      <tr class="odd:bg-slate-100 even:bg-slate-300 hover:bg-slate-400 border-b text-gray-950 text-base"
                      v-for="emergency in emergencies" :key="emergency.id">
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
                        <td class="py-4 flex">
                            <button @click="edit.editReport(emergency.id, emergency.name, emergency.location, emergency.date, emergency.type)" class="text-blue-dark hover:text-blue-200 mr-16">
                              <svg class="h-8 peer" aria-hidden="true" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                <path d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L6.832 19.82a4.5 4.5 0 01-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 011.13-1.897L16.863 4.487zm0 0L19.5 7.125" stroke-linecap="round" stroke-linejoin="round"></path>
                              </svg>
                              <div data-popover id="popover-no-arrow" role="tooltip" class="absolute z-20 -mt-20 -ml-2 text-sm text-gray-500 transition-opacity duration-300 hidden peer-hover:inline-block bg-slate-100 border border-gray-200 rounded-lg shadow-sm">
                                <div class="px-3 py-2">
                                    <p>Edit</p>
                                </div>
                              </div>
                            </button>
                            <a href="#" @click="del.deleteReport(emergency.id, emergency.name)" class="text-blue-dark hover:text-blue-200">
                              <svg class="h-8 peer" aria-hidden="true" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                <path d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" stroke-linecap="round" stroke-linejoin="round"></path>
                              </svg>
                              <div data-popover id="popover-no-arrow" role="tooltip" class="absolute z-20 -mt-20 -ml-4 text-sm text-gray-500 transition-opacity duration-300 hidden peer-hover:inline-block bg-slate-100 border border-gray-200 rounded-lg shadow-sm">
                                <div class="px-3 py-2">
                                    <p>Delete</p>
                                </div>
                              </div>
                            </a>
                        </td>
                      </tr>
                    </tbody>
                </table>
            </div>

          <h1 v-else class="text-xl text-gray-950 font-light mb-14">You haven`t submited any reports yet 👻</h1>

        </div>
</template>