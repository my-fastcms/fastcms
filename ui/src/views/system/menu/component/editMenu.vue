<template>
	<div class="system-menu-container">
		<el-dialog title="编辑路由" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="80px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单名称" prop="title">
							<el-input v-model="ruleForm.title" placeholder="格式：message.router.xxx" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单图标" prop="icon">
							<IconSelector placeholder="请输入菜单图标" v-model="ruleForm.icon" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="路由名称" prop="name">
							<el-input v-model="ruleForm.name" placeholder="路由名称（路由中的name值）" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="路由地址" prop="path">
							<el-input v-model="ruleForm.path" placeholder="路由地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="组件地址" prop="component">
							<el-input v-model="ruleForm.component" placeholder="组件地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="是否隐藏">
							<el-select v-model="ruleForm.isHide" placeholder="请选择是否隐藏" clearable class="w100">
								<el-option label="是" value="true"></el-option>
								<el-option label="否" value="false"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="是否缓存">
							<el-select v-model="ruleForm.isKeepAlive" placeholder="请选择是否缓存" clearable class="w100">
								<el-option label="是" value="true"></el-option>
								<el-option label="否" value="false"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="是否固定">
							<el-select v-model="ruleForm.isAffix" placeholder="请选择是否固定" clearable class="w100">
								<el-option label="是" value="true"></el-option>
								<el-option label="否" value="false"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="是否外链">
							<el-select v-model="ruleForm.isLink" placeholder="请选择是否外链" clearable class="w100" :disabled="ruleForm.isIframe === 'true'">
								<el-option label="是" value="true"></el-option>
								<el-option label="否" value="false"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="是否内嵌">
							<el-select v-model="ruleForm.isIframe" placeholder="请选择是否iframe" clearable class="w100" @change="onSelectIframeChange">
								<el-option label="是" value="true"></el-option>
								<el-option label="否" value="false"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="链接地址">
							<el-input
								v-model="ruleForm.isLink"
								placeholder="外链/内嵌时链接地址（http:xxx.com）"
								clearable
								:disabled="ruleForm.isLink === '' || ruleForm.isLink === 'false'"
							>
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单排序">
							<el-input v-model="ruleForm.sortNum" type="number" placeholder="菜单排序" clearable></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">编 辑</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, getCurrentInstance } from 'vue';
import IconSelector from '/@/components/iconSelector/index.vue';
import { ElMessage } from 'element-plus';
import { saveMenu } from '/@/api/menu/index';
export default {
	name: 'systemEditMenu',
	components: { IconSelector },
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			isShowDialog: false,
			/**
			 * 参数请参考 `/src/router/route.ts` 中的 `dynamicRoutes` 路由菜单格式（请注意参数类型！）
			 * 受到 `element plus` 类型 `string/number/object` 影响，不可使用 `:value="true"`
			 * 的写法，所以传值到后台时，需要转换成布尔值，否则页面可能出现玄学。
			 * 路由权限标识为数组格式，基本都需要自行转换类型
			 */
			ruleForm: {
				id: null,
				name: '', // 路由名称
				path: '',
				component: '', // 组件地址
				// isLink: '', // 是否外链
				sortNum: 0, // 菜单排序
				title: '', // 菜单名称
				icon: '', // 菜单图标
				isHide: '', // 是否隐藏
				isKeepAlive: '', // 是否缓存
				isAffix: '', // 是否固定
				isLink: '', // 是否外链，开启外链条件，`1、isLink:true 2、链接地址不为空`
				isIframe: '', // 是否内嵌，开启条件，`1、isIframe:true 2、链接地址不为空`
			},
			rules: {
				"name": { required: true, message: '请输入路由名称', trigger: 'blur' },
				"path": { required: true, message: '请输入路由地址', trigger: 'blur' },
				"title": { required: true, message: '请输入菜单名称', trigger: 'blur' },
				"component": { required: true, message: '请输入组件地址', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row: any) => {
			state.ruleForm.id = row.id
			state.ruleForm.name = row.name;
			state.ruleForm.path = row.path;
			state.ruleForm.component = row.component;
			state.ruleForm.isLink = row.isLink ? 'true' : '';
			state.ruleForm.sortNum = row.sortNum;
			state.ruleForm.title = row.meta.title;
			// 回显时，图标选择器有这个图标才可以回显，菜单中使用了阿里的、element plus的，二者不可共存
			state.ruleForm.icon = row.meta.icon;
			state.ruleForm.isHide = row.meta.isHide ? 'true' : 'false';
			state.ruleForm.isKeepAlive = row.meta.isKeepAlive ? 'true' : 'false';
			state.ruleForm.isAffix = row.meta.isAffix ? 'true' : 'false';
			state.ruleForm.isLink = row.meta.isLink ? row.isLink : '';
			state.ruleForm.isIframe = row.meta.isIframe ? 'true' : '';
			state.isShowDialog = true;
		};
		// 关闭弹窗
		const closeDialog = (row?: object) => {
			// eslint-disable-next-line no-console
			console.log(row);
			state.isShowDialog = false;
		};
		// 是否内嵌下拉改变
		const onSelectIframeChange = () => {
			if (state.ruleForm.isIframe === 'true') {
				state.ruleForm.isLink = 'true';
			} else {
				state.ruleForm.isLink = '';
			}
		};
		// 取消
		const onCancel = () => {
			closeDialog();
			initForm();
		};
		// 新增
		const onSubmit = () => {
			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					saveMenu(state.ruleForm).then(() => {
						closeDialog(); // 关闭弹窗
						ctx.emit("reloadTable");
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});
		};

		// 表单初始化，方法：`resetFields()` 无法使用
		const initForm = () => {
			state.ruleForm.name = '';
			state.ruleForm.component = '';
			state.ruleForm.isLink = '';
			state.ruleForm.sortNum = 0;
			state.ruleForm.title = '';
			state.ruleForm.icon = '';
			state.ruleForm.isHide = '';
			state.ruleForm.isKeepAlive = '';
			state.ruleForm.isAffix = '';
			state.ruleForm.isLink = '';
			state.ruleForm.isIframe = '';
		};
		return {
			openDialog,
			closeDialog,
			onSelectIframeChange,
			onCancel,
			onSubmit,
			...toRefs(state),
		};
	},
};
</script>
