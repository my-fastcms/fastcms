<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="state.ruleForm" :rules="state.rules" ref="myRefForm" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">阿里云短信</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="短信appKey" prop="aliyun_sms_app_key">
									<el-input v-model="state.ruleForm.aliyun_sms_app_key" placeholder="请输入短信appKey" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="短信appSecret" prop="aliyun_sms_app_secret">
									<el-input v-model="state.ruleForm.aliyun_sms_app_secret" placeholder="请输入短信appSecret" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="是否启用">
									<el-switch
										v-model="state.ruleForm.aliyun_sms_is_enable"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
						</el-row>

						<div class="personal-edit-title mb15">邮箱</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="邮箱地址" prop="email_host">
									<el-input v-model="state.ruleForm.email_host" placeholder="请输入邮箱地址" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="邮箱端口" prop="email_port">
									<el-input v-model="state.ruleForm.email_port" placeholder="请输入邮箱端口" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="邮箱用户名" prop="email_username">
									<el-input v-model="state.ruleForm.email_username" placeholder="请输入邮箱用户名" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="邮箱密码" prop="email_password">
									<el-input v-model="state.ruleForm.email_password" placeholder="请输入邮箱密码" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item>
									<el-button :loading="state.testing" @click="onTestMail">测试邮件配置</el-button>
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

<script lang="ts" setup name="connectionSet">
import { reactive, ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { ConfigApi } from '/@/api/config/index';

const myRefForm = ref();
const configApi = ConfigApi();

const state = reactive({
	fit: "fill",
	testing: false,
	ruleForm: {
		aliyun_sms_app_key: '',
		aliyun_sms_app_secret: '',
		aliyun_sms_is_enable: false,

		email_host: "smtp.163.com",
		email_port: 25,
		email_username: '',
		email_password: '',

	} as any,
	rules: {
		"aliyun_sms_app_key": [
			{required: true, message: '请输入短信appKey', trigger: 'blur'},
		],
		"aliyun_sms_app_secret": [
			{required: true, message: '请输入短信appSecret', trigger: 'blur'},
		],
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

const onTestMail = () => {
	state.testing = true;
	configApi.testMailConfig().then(() => {
		state.testing = false;
		ElMessage.success("邮件配置成功")
	}).catch((res) => {
		state.testing = false;
		ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
	})
};

onMounted(() => {
	let paramKeys = new Array();
	const keys: any[] = Object.keys(state.ruleForm);
	keys.forEach(key => { paramKeys.push(key); });
	let params = qs.stringify( {"configKeys" : paramKeys}, {arrayFormat: 'repeat'});
	configApi.getConfigList(params).then((res) => {
		res.data.forEach((item: any) => {
			state.ruleForm[item.key] = item.jsonValue;
		});
	});
});
</script>

<style scoped lang="scss">
// @import '../../theme/mixins/mixins.scss';
</style>
