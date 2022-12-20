<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="ruleForm" :rules="rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">JWT设置</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="JWT密钥" prop="jwt_secret">
									<el-input v-model="ruleForm.jwt_secret" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item>
									<el-button :loading="loading" @click="onGenJwtSecret">生成JWT密钥</el-button>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="JWT时效" prop="jwt_expire">
									<el-input v-model="ruleForm.jwt_expire" placeholder="请输入JWT时效，整数，单位秒" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						
						<div class="personal-edit-title mb15">注册</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="开启或关闭注册">
									<el-switch
										v-model="ruleForm.public_register_enable"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="密码最短长度" prop="pwd_min_length">
									<el-input v-model="ruleForm.pwd_min_length" placeholder="请输入密码最少长度，整数，默认6" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
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
			loading: false,
			ruleForm: {
				jwt_secret: '',
				jwt_expire: '18000',
				pwd_min_length: 6,
				public_register_enable: false
			},
			rules: {
				"jwt_secret": [
					{required: true, message: '请输入JWT密钥', trigger: 'blur'},
				],
				"jwt_expire": [
					{required: true, message: '请输入JWT时效', trigger: 'blur'},
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
			getConfigList(params).then((res) => {
				res.data.forEach(item => {
					state.ruleForm[item.key] = item.jsonValue;
				});
			});
		});

		return {
			currentTime,
			onGenJwtSecret,
			onSubmit,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
@import '../../theme/mixins/mixins.scss';
</style>
