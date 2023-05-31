<template>
	<div class="system-menu-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="769px">
			<el-form ref="menuDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="80px">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="上级菜单">
							<el-cascader
								ref="parentMenuCascader"
								:options="state.menuData"
								:props="{ checkStrictly: true, value: 'id', label: 'title' }"
								placeholder="请选择上级菜单"
								clearable
								class="w100"
								v-model="state.ruleForm.parentId"
								:emitPath="false"
								:show-all-levels="false"
								@change="onParentMenuChange"
							>
								<template #default="{ node, data }">
									<span>{{ data.title }}</span>
									<span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
								</template>
							</el-cascader>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单名称" prop="title">
							<el-input v-model="state.ruleForm.title" placeholder="格式：message.router.xxx" clearable></el-input>
						</el-form-item>
					</el-col>
					<template v-if="true">
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="路由名称" prop="name">
								<el-input v-model="state.ruleForm.name" placeholder="路由中的 name 值" clearable></el-input>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="路由路径" prop="path">
								<el-input v-model="state.ruleForm.path" placeholder="路由中的 path 值" clearable></el-input>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="菜单图标" prop="icon">
								<IconSelector placeholder="请输入菜单图标" v-model="state.ruleForm.icon" />
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="组件路径" prop="component">
								<el-input v-model="state.ruleForm.component" placeholder="组件路径" clearable></el-input>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="菜单排序" prop="sortNum">
								<el-input-number v-model="state.ruleForm.sortNum" controls-position="right" placeholder="请输入排序" class="w100" />
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="是否外链" prop="isLink">
								<el-radio-group v-model="state.ruleForm.isLink" :disabled="state.ruleForm.isIframe">
									<el-radio :label="true">是</el-radio>
									<el-radio :label="false">否</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="链接地址" prop="link">
								<el-input
									v-model="state.ruleForm.link"
									placeholder="外链/内嵌时链接地址（http:xxx.com）"
									clearable
									:disabled="!state.ruleForm.isLink"
								>
								</el-input>
							</el-form-item>
						</el-col>
					</template>
					
					<template v-if="true">
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="是否隐藏" prop="isHide">
								<el-radio-group v-model="state.ruleForm.isHide">
									<el-radio :label="true">隐藏</el-radio>
									<el-radio :label="false">不隐藏</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="页面缓存" prop="isKeepAlive">
								<el-radio-group v-model="state.ruleForm.isKeepAlive">
									<el-radio :label="true">缓存</el-radio>
									<el-radio :label="false">不缓存</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="是否固定" prop="isAffix">
								<el-radio-group v-model="state.ruleForm.isAffix">
									<el-radio :label="true">固定</el-radio>
									<el-radio :label="false">不固定</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
						<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
							<el-form-item label="是否内嵌" prop="isIframe">
								<el-radio-group v-model="state.ruleForm.isIframe" @change="onSelectIframeChange">
									<el-radio :label="true">是</el-radio>
									<el-radio :label="false">否</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
					</template>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="systemMenuDialog">
import { defineAsyncComponent, reactive, onMounted, ref, nextTick } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoutesList } from '/@/stores/routesList';
import { MenuApi} from '/@/api/menu/index';
import { i18n } from '/@/i18n/index';
// import { setBackEndControlRefreshRoutes } from "/@/router/backEnd";

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 引入组件
const IconSelector = defineAsyncComponent(() => import('/@/components/iconSelector/index.vue'));

const menuApi = MenuApi();
// 定义变量内容
const menuDialogFormRef = ref();
const parentMenuCascader = ref();
const stores = useRoutesList();
const { routesList } = storeToRefs(stores);
const state = reactive({
	// 参数请参考 `/src/router/route.ts` 中的 `dynamicRoutes` 路由菜单格式
	ruleForm: {
		id: null,
		parentId: 0,
		name: '', // 路由名称
		path: '',
		component: '', // 组件地址
		sortNum: 0, // 菜单排序
		title: '', // 菜单名称
		icon: '', // 菜单图标
		link: '',
		isHide: false, // 是否隐藏
		isKeepAlive: true, // 是否缓存
		isAffix: true, // 是否固定
		isLink: false, // 是否外链，开启外链条件，`1、isLink:true 2、链接地址不为空`
		isIframe: false, // 是否内嵌，开启条件，`1、isIframe:true 2、链接地址不为空`
	},
	rules: {
		name: { required: true, message: '请输入路由名称', trigger: 'blur' },
		path: { required: true, message: '请输入路由地址', trigger: 'blur' },
		title: { required: true, message: '请输入菜单名称', trigger: 'blur' },
		component: { required: true, message: '请输入组件地址', trigger: 'blur' },
	},
	menuData: [] as RouteItems, // 上级菜单数据
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
	},
});

// 获取 pinia 中的路由
const getMenuData = (routes: RouteItems) => {
	const arr: RouteItems = [];
	routes.map((val: RouteItem) => {
		val['title'] = i18n.global.t(val.meta?.title as string);
		arr.push({ ...val });
		if (val.children) getMenuData(val.children);
	});
	return arr;
};
// 打开弹窗
const openDialog = (type: string, row?: any) => {
	if (type === 'edit') {
		// 模拟数据，实际请走接口
		state.dialog.title = '修改菜单';
		state.dialog.submitTxt = '修 改';
		menuApi.getMenu(row.id).then(res => {
			delete res.data.created;
			delete res.data.updated;
			state.ruleForm = res.data;
		})
	} else {
		state.dialog.title = '新增菜单';
		state.dialog.submitTxt = '新 增';
		nextTick(() => {
			// 清空表单，此项需加表单验证才能使用
			menuDialogFormRef.value.resetFields();
			state.ruleForm.id = null;
			// 设置菜单上级
			state.ruleForm.parentId = row == null ? 0 : (row.id || 0);
		});
	}
	state.dialog.type = type;
	state.dialog.isShowDialog = true;
};
// 关闭弹窗
const closeDialog = () => {
	state.dialog.isShowDialog = false;
};
// 是否内嵌下拉改变
const onSelectIframeChange = () => {
	if (state.ruleForm.isIframe) state.ruleForm.isLink = true;
	else state.ruleForm.isLink = false;
};
// 取消
const onCancel = () => {
	closeDialog();
};
const onParentMenuChange = () => {
	const p = parentMenuCascader.value.getCheckedNodes();
	// console.log("====p:" + p[0].value)
	state.ruleForm.parentId = p[0].value;
};
// 提交
const onSubmit = () => {
	new Promise((resolve) => {
		menuDialogFormRef.value.validate((valid: boolean) => {
			if (valid) {
				resolve(valid);
				menuApi.saveMenu(state.ruleForm).then(() => {
					closeDialog(); // 关闭弹窗
					emit('refresh');
				})
			}
		});
	});
};
// 页面加载时
onMounted(() => {
	state.menuData = getMenuData(routesList.value);
});

// 暴露变量
defineExpose({
	openDialog,
});
</script>
