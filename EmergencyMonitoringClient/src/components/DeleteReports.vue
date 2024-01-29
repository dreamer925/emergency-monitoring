<script setup lang="ts">
import { reactive } from 'vue';
import { useDeleteStore } from '@/stores/delete';
import { useCrudStore } from '@/stores/crud';

const deleteStore = useDeleteStore();

const emergency = reactive({
    id: deleteStore.emergency.id ? deleteStore.emergency.id : 1,
    name: deleteStore.emergency.name ? deleteStore.emergency.name : '',
});

function onSubmit() {
    console.log('onSubmit')
    useCrudStore().delete(emergency.id);
};

</script>

<template>
    <div class="absolute z-20 mx-auto top-1/3 left-1/3 shadow-2xl rounded-lg w-full max-w-lg h-full md:h-auto">
        <!-- Modal content -->
        <div class="relative p-4 bg-slate-200 rounded-lg shadow-lg sm:p-5">
            <!-- Modal header -->
            <div class="flex justify-between items-center pb-2 mb-2 rounded-t border-b sm:mb-4">
                <h3 class="text-lg font-semibold text-gray-700">
                    Delete an Emergency Report
                </h3>
                <button type="button" @click="deleteStore.finish()" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="updateProductModal">
                    <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                    <span class="sr-only">Close modal</span>
                </button>
            </div>
            <!-- Modal body -->
            <form @submit.prevent="onSubmit">

                <p class="font-medium">Are you sure you want to delete the <b>"{{ emergency.name }}"</b> report?</p>
                
                <div class="flex items-center place-content-center space-x-4 mt-7">
                    <button type="button" @click="deleteStore.finish()" class="text-blue-light bg-gray-50 border border-blue-dark hover:text-white hover:bg-blue-dark focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                        Discard
                    </button>
                    <button type="submit" class="text-red-600 bg-gray-50 inline-flex items-center hover:text-white border border-red-600 hover:bg-red-600 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                        <svg class="mr-1 -ml-1 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd"></path></svg>
                        Delete
                    </button>
                </div>
            </form>
        </div>
      </div>
</template>@/stores/notification