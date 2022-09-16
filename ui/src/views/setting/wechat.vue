<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="ruleForm" :rules="rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">公众号设置</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="公众号appId" prop="wechate_mp_appid">
									<el-input v-model="ruleForm.wechate_mp_appid" placeholder="请输入公众号appId" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="公众号appSecret" prop="wechat_mp_secret">
									<el-input v-model="ruleForm.wechat_mp_secret" placeholder="请输入公众号密钥" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="公众号token" prop="wechat_mp_token">
									<el-input v-model="ruleForm.wechat_mp_token" placeholder="请输入公众号token" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="公众号消息密钥" prop="wechat_mp_aeskey">
									<el-input v-model="ruleForm.wechat_mp_aeskey" placeholder="请输入公众号消息密钥" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>

                        <div class="personal-edit-title mb15">小程序设置</div>
                        <el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="小程序appId" prop="wechat_miniapp_appid">
									<el-input v-model="ruleForm.wechat_miniapp_appid" placeholder="请输入小程序appId" clearable></el-input>
								</el-form-item>
								<el-form-item label="小程序appSecret" prop="wechat_miniapp_secret">
									<el-input v-model="ruleForm.wechat_miniapp_secret" placeholder="请输入小程序密钥" clearable></el-input>
								</el-form-item>
								<el-form-item label="小程序token" prop="wechat_miniapp_token">
									<el-input v-model="ruleForm.wechat_miniapp_token" placeholder="请输入小程序token" clearable></el-input>
								</el-form-item>
								<el-form-item label="小程序消息密钥" prop="wechat_miniapp_aeskey">
									<el-input v-model="ruleForm.wechat_miniapp_aeskey" placeholder="请输入小程序消息密钥" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>

                        <el-col>
                            <el-form-item>
                                <el-button type="primary"  @click="onSubmit" icon="el-icon-position">保 存</el-button>
                            </el-form-item>
						</el-col>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { formatAxis } from '/@/utils/formatTime';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { saveConfig, getConfigList } from '/@/api/config/index';

export default {
	name: 'websiteSet',
	components: { },
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			fit: "fill",
			ruleForm: {
				wechate_mp_appid: '',
				wechat_mp_secret: '',
				wechat_mp_token: '',
				wechat_mp_aeskey: '',
				wechat_miniapp_appid: '',
				wechat_miniapp_secret: '',
				wechat_miniapp_token: '',
				wechat_miniapp_aeskey: '',
			},
			rules: {
				"wechate_mp_appid": [
					{required: true, message: '请输入公众号appId', trigger: 'blur'},
				],
				"wechat_mp_secret": [
					{required: true, message: '请输入公众号密钥', trigger: 'blur'},
				],
				"wechat_mp_token": [
					{required: true, message: '请输入公众号token', trigger: 'blur'},
				],
				"wechat_miniapp_appid": [
					{required: true, message: '请输入小程序appId', trigger: 'blur'}
				],
				"wechat_miniapp_secret": [
					{required: true, message: '请输入小程序appSecret', trigger: 'blur'}
				],
				"wechat_miniapp_token": [
					{required: true, message: '请输入小程序token', trigger: 'blur'}
				],  
			}
		});

		// 当前时间提示语
		const currentTime = computed(() => {
			return formatAxis(new Date());
		});

		const onSubmit = () => {
			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					saveConfig(params).then(() => {
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
			getConfigList(params).then((res) => {
				res.data.forEach(item => {
					state.ruleForm[item.key] = item.jsonValue;
				});
			});
		});

		return {
			currentTime,
			onSubmit,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
@import '../../theme/mixins/mixins.scss';
</style>
