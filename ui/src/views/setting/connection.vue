<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="ruleForm" :rules="rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">阿里云短信</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="短信appKey" prop="aliyun_sms_app_key">
									<el-input v-model="ruleForm.aliyun_sms_app_key" placeholder="请输入短信appKey" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="短信appSecret" prop="aliyun_sms_app_secret">
									<el-input v-model="ruleForm.aliyun_sms_app_secret" placeholder="请输入短信appSecret" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="是否启用">
									<el-switch
										v-model="ruleForm.aliyun_sms_is_enable"
										active-color="#13ce66">
									</el-switch>
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
import { toRefs, ref, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { formatAxis } from '/@/utils/formatTime';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { saveConfig, getConfigList } from '/@/api/config/index';

export default {
	name: 'connectionSet',
	components: { },
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			fit: "fill",
			ruleForm: {
				aliyun_sms_app_key: '',
				aliyun_sms_app_secret: '',
				aliyun_sms_is_enable: false
			},
			rules: {
				"aliyun_sms_app_key": [
					{required: true, message: '请输入短信appKey', trigger: 'blur'},
				],
				"aliyun_sms_app_secret": [
					{required: true, message: '请输入短信appSecret', trigger: 'blur'},
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
