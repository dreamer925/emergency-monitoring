import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/RegisterView.vue'
import ConfirmRegistrationView from '../views/ConfirmRegistrationView.vue'
import TrackerView from '../views/TrackerView.vue'
import ModerationView from '../views/ModerationView.vue'
import ReportsView from '../views/ReportsView.vue'
import AddReportsView from '../views/AddReportsView.vue'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'About',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/signup/confirmRegistration',
      name: 'ConfirmRegistration',
      component: ConfirmRegistrationView
    },
    {
      path: '/signup',
      name: 'Signup',
      component: SignupView
    },
    {
      path: '/tracker',
      name: 'Tracker',
      component: TrackerView
    },
    {
      path: '/moderation',
      name: 'Moderation',
      component: ModerationView
    },
    {
      path: '/reports',
      name: 'Reports',
      component: ReportsView
    },
    {
      path: '/reports/add',
      name: 'AddReports',
      component: AddReportsView
    }
  ]
});

router.beforeEach(async (to) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/login', '/signup', '/signup/confirmRegistration', '/', '/about', '/tracker'];
  const authRequired = !publicPages.includes(to.path);
  const auth = useAuthStore();

  if (authRequired && !auth.user) {
      auth.returnUrl = to.fullPath;
      return '/login';
  }
});

export default router
