import { createWebHistory, createRouter, RouteRecordRaw } from 'vue-router';
/* Layout */
import Layout from '@/layout/index.vue';
import { getInfo as getUserInfo } from '@/api/login';
import { useRouter } from 'vue-router';

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/social-callback',
    hidden: true,
    component: () => import('@/layout/components/SocialCallback/index.vue')
  },
  {
    path: '/login',
    component: () => import('@/views/login.vue'),
    hidden: true
  },
  {
    path: '/fl_farmer',
    name: '农户页面',
    component: () => import('@/views/farmer/index.vue'),
    hidden: true,
    children: [
      {
        path: '/home',
        component: () => import('@/views/farmer/home/index.vue'),
        name: '首页'
      },
      {
        path: '/resource',
        component: () => import('@/views/farmer/resource/index.vue'),
        name: '商品资源'
      },
      {
        path: '/detail/:id',
        component: () => import('@/views/farmer/resource/detail/index.vue'),
        name: '商品详情'
      },
      {
        path: '/demand',
        component: () => import('@/views/farmer/demand/index.vue'),
        name: '求购需求'
      },
      {
        path: '/knowledge',
        component: () => import('@/views/farmer/knowledge/index.vue'),
        name: '农业知识'
      },
      {
        path: '/knowledge/:id',
        component: () => import('@/views/farmer/knowledge/detail/index.vue'),
        name: '农业知识详情'
      },
      {
        path: '/guidance',
        component: () => import('@/views/farmer/guidance/index.vue'),
        name: '专家指导'
      },
      {
        path: '/shopping',
        component: () => import('@/views/farmer/shopping/index.vue'),
        name: '购物车'
      },
      {
        path: '/financing',
        component: () => import('@/views/farmer/financing/index.vue'),
        name: '融资申请'
      },
      {
        path: '/setting',
        component: () => import('@/views/farmer/setting/index.vue'),
        name: '设置',
        children: [
          {
            path: '/information',
            component: () => import('@/views/farmer/setting/information/index.vue'),
            name: '基本信息'
          },
          {
            path: '/address',
            component: () => import('@/views/farmer/setting/address/index.vue'),
            name: '收货地址'
          },
          {
            path: '/changePwd',
            component: () => import('@/views/farmer/setting/changePwd/index.vue'),
            name: '修改密码'
          },
          {
            path: '/issue',
            component: () => import('@/views/farmer/setting/issue/index.vue'),
            name: '我的发布'
          },
          {
            path: '/questions',
            component: () => import('@/views/farmer/setting/questions/index.vue'),
            name: '我的问题'
          },
          {
            path: '/myappointment',
            component: () => import('@/views/farmer/setting/myappointment/index.vue'),
            name: '我的预约'
          },
          {
            path: '/myknowledge',
            component: () => import('@/views/farmer/setting/myknowledge/index.vue'),
            name: '我的知识'
          },
          {
            path: '/orderBuy',
            component: () => import('@/views/farmer/setting/orderBuy/index.vue'),
            name: '我买的'
          },
          {
            path: '/orderSell',
            component: () => import('@/views/farmer/setting/orderSell/index.vue'),
            name: '我卖的'
          },
          {
            path: '/myfinancing',
            component: () => import('@/views/farmer/setting/myfinancing/index.vue'),
            name: '我的融资'
          },
          {
            path: '/specialist',
            component: () => import('@/views/farmer/setting/specialist/index.vue'),
            name: '申请成为专家'
          },
          {
            path: '/expertInformation',
            component: () => import('@/views/farmer/setting/expertInformation/index.vue'),
            name: '专家信息'
          },
          {
            path: '/answers',
            component: () => import('@/views/farmer/setting/answers/index.vue'),
            name: '我的回答'
          },
          {
            path: '/appointment',
            component: () => import('@/views/farmer/setting/appointment/index.vue'),
            name: '预约信息'
          }
        ]
      }
    ]
  },
  {
    path: '/fl_specialist',
    component: () => import('@/views/specialist/index.vue'),
    hidden: true
  },
  {
    path: '/fl_bank_user',
    component: () => import('@/views/bank_user/index.vue'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register.vue'),
    hidden: true
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/error/404.vue'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401.vue'),
    hidden: true
  },
  {
    path: '',
    hidden: true,
    redirect: '/home'
  },
  {
    path: '/index',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: '',
        component: () => import('@/views/index.vue'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index.vue'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
];

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes: RouteRecordRaw[] = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole.vue'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user', icon: '' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser.vue'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role', icon: '' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data.vue'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict', icon: '' }
      }
    ]
  },
  {
    path: '/system/oss-config',
    component: Layout,
    hidden: true,
    permissions: ['system:ossConfig:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/system/oss/config.vue'),
        name: 'OssConfig',
        meta: { title: '配置管理', activeMenu: '/system/oss', icon: '' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable.vue'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen', icon: '', noCache: true }
      }
    ]
  },
  {
    path: '/workflow/leaveEdit',
    component: Layout,
    hidden: true,
    permissions: ['workflow:leave:edit'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/workflow/leave/leaveEdit.vue'),
        name: 'leaveEdit',
        meta: { title: '请假申请', activeMenu: '/workflow/leave', noCache: true }
      }
    ]
  }
];

/**
 * 创建路由
 */
const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_APP_CONTEXT_PATH),
  routes: constantRoutes,
  // 刷新时，滚动条位置还原
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  }
});
//路由全局前置守卫 把/index的请求打到对应的页面
const MAX_RETRIES = 3; // 最大重试次数
let retryCount = 0;

router.beforeEach(async (to, from, next) => {
  if (to.path === '/index' || to.path === '/') {
    try {
      const res = await getUserInfo();
      const role = res.data.roles[0];
      console.log(role);

      // 根据角色重定向
      if (role === 'fl_farmer') {
        next('/home');
      } else if (role === 'fl_specialist') {
        next('/home');
      } else if (role === 'fl_bank_user') {
        next('/fl_bank_user');
      } else {
        next(); // 如果角色不匹配，继续
      }

      retryCount = 0; // 重置重试计数
    } catch (error) {
      console.error('Error fetching user info:', error);

      // 增加重试逻辑
      if (retryCount < MAX_RETRIES) {
        retryCount++;
        setTimeout(() => {
          next('/index'); // 再次尝试
        }, 1000); // 1秒后重试
      } else {
        next('/login'); // 达到最大重试次数，重定向到登录页
      }
    }
  } else {
    next();
  }
});

export default router;
