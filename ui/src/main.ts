import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { store, key } from './store';
import { directive } from '/@/utils/directive';
import { i18n } from '/@/i18n/index';

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import '/@/theme/index.scss';
import "./theme/ck-vite.css";
import mitt from 'mitt';
import screenShort from 'vue-web-screen-shot';
import VueGridLayout from 'vue-grid-layout';

const app = createApp(App);
 app.use(router)
	.use(store, key)
	.use(ElementPlus)
	.use(i18n)
	.use(screenShort, { enableWebRtc: false })
	.use(VueGridLayout)
	.mount('#app');

app.config.globalProperties.mittBus = mitt();
console.log(app);

directive(app);
