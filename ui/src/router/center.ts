export const centerRoute = [
    {
        path: '/center',
        name: 'center',
        component: () => import('/@/centerLayout/index.vue'),
        // redirect: '/center/centerHome',
        meta: {
            isKeepAlive: true,
        },
        children: [
            {
                path: '/center/centerHome',
                name: 'centerHome',
                component: () => import('/@/views/center/centerHome/index.vue'),
                meta: {
                    title: '个人中心',
                    isLink: '',
                    isHide: false,
                    isKeepAlive: true,
                    isAffix: true,
                    isIframe: false,
                    auth: ['admin', 'test'],
                    icon: 'iconfont icon-shouye',
                },
            }
        ],
    }
];
