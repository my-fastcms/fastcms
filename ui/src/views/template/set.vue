<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="ruleForm" :rules="rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
                        <el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="是否开启网站静态化">
									<el-switch
										v-model="ruleForm.enableStaticWebSite"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>

                            <el-col class="mb20">
								<el-form-item label="是否开启伪静态">
									<el-switch
										v-model="ruleForm.enableFakeStatic"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
							
							<el-col class="mb20">
								<el-form-item label="伪静态访问后缀">
									<el-input v-model="ruleForm.fakeStaticSuffix" placeholder="请输入伪静态访问后缀" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
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
		<AttachDialog ref="attachDialogRef" @attachHandler="getSelectAttach"/>
	</div>
</template>

<script lang="ts">
import { toRefs, ref, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { formatAxis } from '/@/utils/formatTime';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { saveConfig, getConfigList } from '/@/api/config/index';
import AttachDialog from '/@/components/attach/index.vue';

export default {
	name: 'attachSet',
	components: { AttachDialog },
	setup() {
		const attachDialogRef = ref();
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			fit: "fill",
			ruleForm: {
				enableStaticWebSite: false,
                enableFakeStatic: false,
				fakeStaticSuffix: '.html',
			},
			rules: {
				"fakeStaticSuffix": [
					{required: true, message: '请输入伪静态访问后缀', trigger: 'blur'},
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

		//获取弹出框选中的附件
		const getSelectAttach = (value) => {
			state.ruleForm.waterMarkFile = value[0].path;
		};

		//打开附件弹出框
		const onAttachDialogOpen = () => {
			attachDialogRef.value.openDialog(1);
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
			getSelectAttach,
			attachDialogRef,
			currentTime,
			onSubmit,
			onAttachDialogOpen,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
@import '../../theme/mixins/mixins.scss';
</style>
