import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import { resolve } from 'path';
import { ConfigEnv, UserConfigExport, defineConfig, loadEnv } from 'vite';
import viteCompression from 'vite-plugin-compression';
import vueSetupExtend from 'vite-plugin-vue-setup-extend-plus';
import CKEditorSvgLoader from './CKEditorSvgLoader';
import { buildConfig } from './src/utils/build';

const pathResolve = (dir: string) => {
	return resolve(__dirname, '.', dir);
};

const alias: Record<string, string> = {
	'/@': pathResolve('./src/'),
	'vue-i18n': 'vue-i18n/dist/vue-i18n.cjs.js',
};

const viteConfig: UserConfigExport = defineConfig(({ mode }: ConfigEnv) => {
	const env = loadEnv(mode, process.cwd());
	return {
		plugins: [
			vue(),
			vueJsx(),
			CKEditorSvgLoader(),
			vueSetupExtend(),
			viteCompression(),
			JSON.parse(env.VITE_OPEN_CDN) ? buildConfig.cdn() : null,
		].filter(Boolean),
		root: process.cwd(),
		resolve: { alias },
		base: mode === 'serve' ? './' : env.VITE_PUBLIC_PATH,
		optimizeDeps: {
			exclude: ['vue-demi'],
		},
		server: {
			host: '0.0.0.0',
			port: Number(env.VITE_PORT),
			open: JSON.parse(env.VITE_OPEN),
			hmr: true,
			proxy: {
				'/gitee': {
					target: 'https://gitee.com',
					ws: true,
					changeOrigin: true,
					rewrite: (path) => path.replace(/^\/gitee/, ''),
				},
			},
		},
		build: {
			outDir: 'dist',
			chunkSizeWarningLimit: 1500,
			rollupOptions: {
				output: {
					chunkFileNames: 'assets/js/[name]-[hash].js',
					entryFileNames: 'assets/js/[name]-[hash].js',
					assetFileNames: 'assets/[ext]/[name]-[hash].[ext]',
					manualChunks(id) {
						if (id.includes('node_modules')) {
							return id.toString().match(/\/node_modules\/(?!.pnpm)(?<moduleName>[^\/]*)\//)?.groups!.moduleName ?? 'vender';
						}
					},
				},
				input: {
					index: resolve(__dirname, 'fastcms.html'),
				},
				...(JSON.parse(env.VITE_OPEN_CDN) ? { external: buildConfig.external } : {}),
			},
		},
		css: {
			preprocessorOptions: {
				scss: {
					additionalData: '@use "sass:math"; @use "sass:color";',
				},
			},
			devSourcemap: true,
		},
		define: {
			__VUE_I18N_LEGACY_API__: JSON.stringify(false),
			__VUE_I18N_FULL_INSTALL__: JSON.stringify(false),
			__INTLIFY_PROD_DEVTOOLS__: JSON.stringify(false),
			__NEXT_VERSION__: JSON.stringify(process.env.npm_package_version),
			__NEXT_NAME__: JSON.stringify(process.env.npm_package_name),
		},
	};
});

export default viteConfig;
