<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="state.ruleForm" :rules="state.rules" ref="myRefForm" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">JWT设置</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="JWT密钥" prop="jwt_secret">
									<el-input v-model="state.ruleForm.jwt_secret" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item>
									<el-button :loading="state.loading" @click="onGenJwtSecret">生成JWT密钥</el-button>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="JWT时效" prop="jwt_expire">
									<el-input v-model="state.ruleForm.jwt_expire" placeholder="请输入JWT时效，整数，单位秒" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>

						<div class="personal-edit-title mb15">登录</div>
						
						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="是否开启公众号扫码登录">
									<el-switch
										v-model="state.ruleForm.public_mp_scan_qrcode_login_enable"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="是否默认使用公众号扫码登录">
									<el-switch
										v-model="state.ruleForm.public_mp_scan_qrcode_login_is_default"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="允许错误密码输入最大次数" prop="allow_pwd_error_count">
									<el-input v-model="state.ruleForm.allow_pwd_error_count" placeholder="" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="是否启用找回密码功能">
									<el-switch
										v-model="state.ruleForm.public_forgot_password_enable"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

						</el-row>

						
						<div class="personal-edit-title mb15">注册</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="是否开启注册功能">
									<el-switch
										v-model="state.ruleForm.public_register_enable"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="密码最短长度" prop="pwd_min_length">
									<el-input v-model="state.ruleForm.pwd_min_length" placeholder="请输入密码最少长度，整数，默认6" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
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
import { reactive, ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { ConfigApi } from '/@/api/config/index';

const configApi = ConfigApi();
const myRefForm = ref();
const state = reactive({
	fit: "fill",
	loading: false,
	ruleForm: {
		jwt_secret: '',
		jwt_expire: '18000',
		pwd_min_length: 6,
		public_register_enable: false,
		public_mp_scan_qrcode_login_enable: false,
		public_mp_scan_qrcode_login_is_default: false,
		public_forgot_password_enable: true,
		allow_pwd_error_count: 10
	} as any,
	rules: {
		"jwt_secret": [
			{required: true, message: '请输入JWT密钥', trigger: 'blur'},
		],
		"jwt_expire": [
			{required: true, message: '请输入JWT时效', trigger: 'blur'},
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

const onGenJwtSecret = () => {
	const len = 69
	const codeList = []
	const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789'
	const charsLen = chars.length
	for (let i = 0; i < len; i++) {
		codeList.push(chars.charAt(Math.floor(Math.random() * charsLen)))
	}
	let str = codeList.join('')
	state.ruleForm.jwt_secret = str;
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
