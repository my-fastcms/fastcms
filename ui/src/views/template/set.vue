<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="state.ruleForm" :rules="state.rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
                        <el-row :gutter="35">

							<el-col class="mb20">
								<el-form-item label="是否开启伪静态">
									<el-switch
										v-model="state.ruleForm.enableFakeStatic"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
							
							<el-col class="mb20">
								<el-form-item label="伪静态访问后缀" prop="fakeStaticSuffix">
									<el-input v-model="state.ruleForm.fakeStaticSuffix" placeholder="请输入伪静态访问后缀" readonly clearable></el-input>
								</el-form-item>
							</el-col>
							
						</el-row>
                        <el-col>
                            <el-form-item>
                                <el-button type="primary" @click="onSubmit" icon="el-icon-position">保 存</el-button>
                            </el-form-item>
						</el-col>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script lang="ts" name="templateSet" setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { ConfigApi } from '/@/api/config/index';

const configApi = ConfigApi();
const myRefForm = ref();
const state = reactive({
	fit: "fill",
	ruleForm: {
		enableStaticWebSite: false,
		enableFakeStatic: false,
		fakeStaticSuffix: '.html',
	} as any,
	rules: {
		"fakeStaticSuffix": [
			{required: true, message: '请输入伪静态访问后缀', trigger: 'blur'},
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
