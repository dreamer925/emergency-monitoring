import { defineStore } from 'pinia';
import router from '@/router';
import { useNotificationStore } from '@/stores/notification';

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => {
    return {
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!) : null,
      token: localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')!) : null,
      name: localStorage.getItem('name') ? JSON.parse(localStorage.getItem('name')!) : null,
      role: localStorage.getItem('role') ? JSON.parse(localStorage.getItem('role')!) : null,

      returnUrl: '/'
    }
  },
  actions: {
    async login(username: string, password: string, remember: string) {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password, remember })
      });

      if (response.status === 200) {

        const res  = await response.json();

        const token = await res.token;
        const name = await res.name;
        const role = await res.role;

        localStorage.setItem('user', JSON.stringify(username));
        localStorage.setItem('token', JSON.stringify(token));
        localStorage.setItem('name', JSON.stringify(name));
        localStorage.setItem('role', JSON.stringify(role));

        this.user = username;
        this.token = token;
        this.name = name;
        this.role = role;

        router.push(this.returnUrl || '/');
      } else {
        const errorMessage = await response.text();
        console.log(errorMessage);

        const notify = useNotificationStore();
        notify.setError('Invalid credentials');
        // throw new Error('Invalid credentials');
      }

    },
    async signup(name: string, email: string, password: string) {
      console.log(JSON.stringify({ name, email, password }));
      const response = await fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email, password })
      });

      if (response.status === 200) {

        router.push('/signup/confirmRegistration');

      } else {
        const errorMessage = await response.text();
        console.log(errorMessage);
        
        const errorStore = useNotificationStore();
        errorStore.setError(errorMessage);
      }
    },
    logout() {
      this.user = '';
      this.token = '';
      this.name = '';
      this.role = '';

      localStorage.removeItem('user');
      localStorage.removeItem('token');
      localStorage.removeItem('name');
      localStorage.removeItem('role');

      router.push('/login');
    }
  }
});