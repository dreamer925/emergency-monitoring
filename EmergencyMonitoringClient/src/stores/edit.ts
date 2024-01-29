import { defineStore } from 'pinia';
  
  export const useEditStore = defineStore({
    id: 'edit',
    state: () => {
      return {
        state:'off' as string,
        emergency: {
            id: null as number | null,
            name: null as string | null,
            location: {
                country: null as string | null,
                city: null as string | null,
            },
            date: null as Date | null,
            type: null as string | null,
        },
      };
    },
    actions: {
      editReport(id:number, name:string, location:{country:string, city:string}, date:Date, type:string) {
        console.log('editStore');
        console.log(id + '' + name + '' + location.city + '' + location.country + '' + date + '' + type);
        this.emergency.id = id;
        this.emergency.name = name;
        this.emergency.location = location;
        this.emergency.date = date;
        this.emergency.type = type;
        this.state = 'on';
      },
      finishEditing() {
        this.state = 'off';
      },
    },
  });