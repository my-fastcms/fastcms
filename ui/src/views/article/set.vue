<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="state.ruleForm" :rules="state.rules" ref="myRefForm" label-width="150px" class="mt35 mb35">
                        
                        <div class="personal-edit-title mb15">评论设置</div>

						<el-row :gutter="35">
							
							<el-col class="mb20">
								<el-form-item label="是否开启评论功能">
									<el-switch
										v-model="state.ruleForm.enableArticleComment"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="评论是否必须经过管理员审核">
									<el-switch
										v-model="state.ruleForm.enableArticleCommentAdminVerify"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

							<el-col class="mb20">
								<el-form-item label="启用评论验证码功能">
									<el-switch
										v-model="state.ruleForm.enableArticleCommentVerifyCode"
										active-color="#13ce66">
									</el-switch>
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

<script lang="ts" setup name="attachSet">
import { reactive, ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { ConfigApi } from '/@/api/config/index';

const myRefForm = ref();
const configAPi = ConfigApi();
const state = reactive({
	fit: "fill",
	ruleForm: {
		enableArticleComment: true,
		enableArticleCommentAdminVerify: false,
		enableArticleCommentVerifyCode: true,
	} as any,
	rules: {
		
	}
});

const onSubmit = () => {
	myRefForm.value.validate((valid: any) => {
		if (valid) {
			let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
			configAPi.saveConfig(params).then(() => {
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
	configAPi.getConfigList(params).then((res) => {
		res.data.forEach((item: any) => {
			state.ruleForm[item.key] = item.jsonValue;
		});
	});
});
</script>

<style scoped lang="scss">
// @import '../../theme/mixins/mixins.scss';
</style>
