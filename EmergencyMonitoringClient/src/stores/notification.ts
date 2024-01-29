import { defineStore } from 'pinia';
  
  export const useNotificationStore = defineStore({
    id: 'notification',
    state: () => {
      return {
        error: null as string | null,
        success: null as string | null,
      };
    },
    actions: {
      setError(error: string) {
        console.log('errorNotification')
        this.error = error;
        setTimeout(() => {
          this.clearError();
        }, 10000); // Duration
      },
      clearError() {
        this.error = null;
      },
      setSuccessMessage(success: string) {
        console.log('successNotification')
        this.success = success;
        setTimeout(() => {
          this.clearSuccessMessage();
        }, 10000); // Duration
      },
      clearSuccessMessage() {
        this.success = null;
      },
    },
  });