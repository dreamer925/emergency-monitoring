import { defineStore } from 'pinia';
import router from '@/router';
import { useAuthStore } from '@/stores/auth';
import { reactive } from 'vue';
import { HttpStatusCode } from 'axios';
import { useNotificationStore } from '@/stores/notification';

//Todo: cut getEmergency()

function getEmergency (name: string, date: Date, type: string, country?: string, city?: string) {
    const emergency = reactive({
        name: '',
        location: {
            country: '',
            city: '',
        },
        date: new Date(),
        type: '',
    })

    emergency.name = name;
    emergency.date = date;
    emergency.type = type;
    emergency.location.country = country ? country : '';
    emergency.location.city = city ? city : '';

    return emergency;
}

export const useCrudStore = defineStore({
  id: 'crud',
  state: () => {
    return {
      content: false,
      error: '',
      
    //   returnUrl: '/'
    }
  },
  getters: {
    token() {
        const auth = useAuthStore();
        return auth.token
    }
  },
  actions: {
    async create(name: string, date: Date, type: string, country?: string, city?: string) {

            const emergency = getEmergency(name, date, type, country, city);
            const token = this.token;
            const notify = useNotificationStore();

            console.log(emergency);

            const response = await fetch('http://localhost:8080/api/emergencies', {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name, city, country, date, type })
              });
        
              console.log(emergency);
        
              if (response.status === HttpStatusCode.Created) {
                this.content = true;
                const successMessage = "New emergency successfully added";
                console.log(successMessage);
                notify.setSuccessMessage(successMessage);
              } else if (response.status === HttpStatusCode.BadRequest) {
                console.log('emAdd BadRequest');
                this.content = false;
                this.error = 'BadRequest'
                const errorMessage = await response.text();
                notify.setError(errorMessage);
              } else {
                console.log('emAdd error');
                this.error = response.statusText;
                const errorMessage = await response.text();
                notify.setError(errorMessage);
              }

    },
    async update(id:number, name: string, date: Date, type: string, country?: string, city?: string) {

      const emergency = getEmergency(name, date, type, country, city);
      const token = this.token;
      const notify = useNotificationStore();

      console.log(emergency);

      const response = await fetch('http://localhost:8080/api/emergencies/' + id, {
          method: 'PUT',
          headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({ name, city, country, date, type })
        });
  
        console.log(emergency);
  
        if (response.status === HttpStatusCode.NoContent) {
          this.content = true;
          const successMessage = "Emergency successfully updated";
                console.log(successMessage);
                notify.setSuccessMessage(successMessage);
        } else if (response.status === HttpStatusCode.BadRequest) {
          console.log('emUpd BadRequest');
          this.error = 'BadRequest';
          const errorMessage = await response.text();
          notify.setError(errorMessage);
        } else {
          console.log('emUpd error');
          this.error = response.statusText;
          const errorMessage = await response.text();
          notify.setError(errorMessage);
        }

    },
    async delete(id:number) {
      const token = this.token;
      const notify = useNotificationStore();

      const response = await fetch('http://localhost:8080/api/emergencies/' + id, {
          method: 'DELETE',
          headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json'
          },
        });
  
        if (response.status === HttpStatusCode.NoContent) {
          const successMessage = "Emergency successfully deleted";
                console.log(successMessage);
                notify.setSuccessMessage(successMessage);
        } else if (response.status === HttpStatusCode.BadRequest) {
          console.log('emDel BadRequest');
          this.error = 'BadRequest'
          const errorMessage = await response.text();
          notify.setError(errorMessage);
        } else {
          console.log('emDel error');
          this.error = response.statusText;
          const errorMessage = await response.text();
          notify.setError(errorMessage);
        }
    },
  }
});