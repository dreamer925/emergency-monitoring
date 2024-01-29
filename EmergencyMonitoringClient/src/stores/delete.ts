import { defineStore } from 'pinia';
  
  export const useDeleteStore = defineStore({
    id: 'delete',
    state: () => {
      return {
        state:'off' as string,
        emergency: {
            id: null as number | null,
            name: null as string | null,
        },
      };
    },
    actions: {
        deleteReport(id:number, name:string) {
        console.log('deleteStore');
        this.emergency.id = id;
        this.emergency.name = name;
        this.state = 'on';
      },
      finish() {
        this.state = 'off';
      },
    },
  });