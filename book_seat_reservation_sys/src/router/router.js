import { createRouter, createWebHistory } from "vue-router";
import { useGlobalStore } from "../global/globalStore"

const routes = [
  {
    path: "/",
    name: "/",
    redirect: "/front",
  },
  // 前台
  {
    path: "/front",
    name: "front",
    meta: {
      title: "图书馆座位预约",
    },
    component: () => import("../前台/views/Home.vue"),
    redirect: "/front/seat",
    children: [
      {
        path: "/front/seat",
        name: "seat",
        component: () => import("../前台/views/Seat.vue"),
      },
      {
        path: "/front/points",
        name: "points",
        component: () => import("../前台/views/Points.vue"),
      }
    ],
  },
  // 后台
  {
    path: "/back",
    name: "back",
    meta: {
      title: "座位预约管理系统",
    },
    component: () => import("../后台/views/Home.vue"),
    redirect: "/back/home",
    children: [
      {
        path: "/back/home",
        name: "/home",
        meta: {
          title: "系统|首页"
        },
        component: () => import("../后台/views/ContainerHome.vue"),
      },
      {
        path: "/back/user",
        name: "/user",
        meta: {
          title: "系统|用户管理"
        },
        component: () => import("../后台/views/User.vue"),
      },
      {
        path: "/back/seat",
        name: "/seat",
        meta: {
          title: "系统|座位管理"
        },
        component: () => import("../后台/views/Seat.vue"),
      },
      {
        path: "/back/order",
        name: "/order",
        meta: {
          title: "系统|预约管理"
        },
        component: () => import("../后台/views/Order.vue"),
      },
      {
        path: "/back/orderRecord",
        name: "/orderRecord",
        meta: {
          title: "系统|预约日志管理"
        },
        component: () => import("../后台/views/OrderRecord.vue"),
      },
      {
        path: "/back/exchange",
        name: "/exchange",
        meta: {
          title: "系统|兑换物管理"
        },
        component: () => import("../后台/views/Exchange.vue"),
      },
      {
        path: "/back/exchangeRecord",
        name: "/exchangeRecord",
        meta: {
          title: "系统|兑换记录"
        },
        component: () => import("../后台/views/ExchangeRecord.vue"),
      },
      {
        path: "/back/points",
        name: "/points",
        meta: {
          title: "系统|积分管理"
        },
        component: () => import("../后台/views/Points.vue"),
      },
      {
        path: "/back/pointsRecord",
        name: "/pointsRecord",
        meta: {
          title: "系统|积分记录"
        },
        component: () => import("../后台/views/PointsRecord.vue"),
      },
    ]
  },
  // 登录
  {
    path: "/login",
    name: "login",
    meta: {
      title: "登录",
    },
    component: () => import("../views/Login.vue"),
  },
  // 测试
  {
    path: "/test",
    name: "test",
    component: () => import("../test/vuetifyCss/vuetify.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 全局前置路由导航守卫，当路由跳转前触发
router.beforeEach((to, from, next) => {
  const globalStore = useGlobalStore()
  // 如果即将进入的路由有标题，就设置文档标题
  if (to.meta && to.meta.title) {
    document.title = to.meta.title;
  }
  // 判断是否登录，如果没有登录，就跳转到登录页面
  if (to.path !== "/login" && (!globalStore.getStudentId() && !globalStore.getAdminId())) {
    next("/login");
  } else {
    next();
  }
  next();
});
export default router;
