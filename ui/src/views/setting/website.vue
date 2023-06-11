<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="state.ruleForm" :rules="state.rules" ref="myRefForm" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">常规设置</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="网站名称" prop="website_name">
									<el-input v-model="state.ruleForm.public_website_name" placeholder="请输入网站名称" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="网站标题" prop="website_title">
									<el-input v-model="state.ruleForm.public_website_title" placeholder="请输入网站标题" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="网站SEO" prop="website_seo">
									<el-input v-model="state.ruleForm.website_seo" placeholder="请输入网站SEO" clearable></el-input>
									<div class="sub-title">SEO关键字设置</div>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="网站域名" prop="public_website_domain">
									<el-input v-model="state.ruleForm.public_website_domain" placeholder="请输入网站域名" clearable></el-input>
									<div class="sub-title">请填写http（或https）开头，结尾不要包含“/”。例如：http://www.xjd2020.com</div>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="是否显示网站logo">
									<el-switch
										v-model="state.ruleForm.is_show_fastcms_logo"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
						</el-row>

                        <div class="personal-edit-title mb15">管理员</div>
                        <el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="手机号">
									<el-input v-model="state.ruleForm.website_admin_mobile" placeholder="请输入手机号" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
								<el-form-item label="微信">
									<el-input v-model="state.ruleForm.website_admin_wechat" placeholder="请输入微信号" clearable></el-input>
								</el-form-item>
								<el-form-item label="QQ">
									<el-input v-model="state.ruleForm.website_admin_qq" placeholder="请输入QQ号" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
								<el-form-item label="邮箱">
									<el-input v-model="state.ruleForm.website_admin_email" placeholder="请输入邮箱" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>

						<div class="personal-edit-title mb15">其他</div>
                        <el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="备案号">
									<el-input v-model="state.ruleForm.public_website_case_num" placeholder="请输入备案号" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="版权信息">
									<el-input v-model="state.ruleForm.public_website_copyright" placeholder="请输入版权信息" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>

                        <el-col>
                            <el-form-item>
                                <el-button type="primary"  @click="onSubmit" size="default">保 存</el-button>
                            </el-form-item>
						</el-col>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script lang="ts" setup name="websiteSet">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { ConfigApi } from '/@/api/config/index';

const configApi = ConfigApi();
const myRefForm = ref();
const state = reactive({
	fit: "fill",
	ruleForm: {
		public_website_title: '',
		public_website_name: '',
		public_website_domain: '',
		website_seo: '',
		website_admin_mobile: '',
		website_admin_wechat: '',
		website_admin_qq: '',
		website_admin_email: '',
		public_website_case_num: '',
		public_website_copyright: '',
		is_show_fastcms_logo: false
	} as any,
	rules: {
		"public_website_title": [
			{required: true, message: '请输入网站标题', trigger: 'blur'},
		],
		"public_website_name": [
			{required: true, message: '请输入网站名称', trigger: 'blur'},
		],
		"public_website_domain": [
			{required: true, message: '请输入网站域名', trigger: 'blur'}
		]  
	}
});

const onSubmit = () => {
	myRefForm.value.validate((valid: any) => {
		if (valid) {
			let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
			configApi.saveConfig(params).then(() => {
				ElMessage.success("保存成功")
			}).catch((res) => {
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}
	});
};

onMounted(() => {
	let paramKeys = new Array();
	const keys: any[] = Object.keys(state.ruleForm);
	keys.forEach(key => { paramKeys.push(key); });
	let params = qs.stringify( {"configKeys" : paramKeys}, {arrayFormat: 'repeat'});
	configApi.getConfigList(params).then((res: any) => {
		res.data.forEach((item: any) => {
			state.ruleForm[item.key] = item.jsonValue;
		});
	});
});
</script>

<style scoped lang="scss">
// @import '../../theme/mixins/mixins.scss';
</style>
