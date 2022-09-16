<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="ruleForm" :rules="rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">评论设置</div>

						<el-row :gutter="35">
							
							<el-col class="mb20">
								<el-form-item label="是否开启评论功能">
									<el-switch
										v-model="ruleForm.enablePageComment"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="评论是否必须经过管理员审核">
									<el-switch
										v-model="ruleForm.enablePageCommentAdminVerify"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="启用评论验证码功能">
									<el-switch
										v-model="ruleForm.enablePageCommentVerifyCode"
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
import { toRefs, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { formatAxis } from '/@/utils/formatTime';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { saveConfig, getConfigList } from '/@/api/config/index';

export default {
	name: 'pageSet',
	components: { },
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			fit: "fill",
			ruleForm: {
				enablePageComment: true,
				enablePageCommentAdminVerify: false,
				enablePageCommentVerifyCode: true,
			},
			rules: {
				
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
