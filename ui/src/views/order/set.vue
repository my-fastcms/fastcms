<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="state.ruleForm" :rules="state.rules" ref="myRefForm" label-width="150px" class="mt35 mb35">
                        
						<div class="personal-edit-title mb15">订单设置</div>

                        <el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="开启自动取消订单" prop="enableAutoCancelOrder">
									<el-switch
										v-model="state.ruleForm.enableAutoCancelOrder"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
							
							<el-col class="mb20">
								<el-form-item label="未支付取消订单时间" prop="unPayOrderCancelTime">
									<el-input v-model="state.ruleForm.unPayOrderCancelTime" placeholder="请输入订单取消时间，单位分钟" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
							
						</el-row>

						<div class="personal-edit-title mb15">提现设置</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="是否开启提现审核" prop="enableAmountCashOutAudit">
									<el-switch
										v-model="state.ruleForm.enableAmountCashOutAudit"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="单日提现最大次数" prop="cashOutAmountDayMaxTimeValue">
									<el-input v-model="state.ruleForm.cashOutAmountDayMaxTimeValue" placeholder="0表示不限制" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="单次提现最大金额" prop="cashOutAmountDayMaxValue">
									<el-input v-model="state.ruleForm.cashOutAmountDayMaxValue" placeholder="单位元，0表示不限制" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="余额满多少金额允许提现" prop="cashOutAmountDayBalanceMaxValue">
									<el-input v-model="state.ruleForm.cashOutAmountDayBalanceMaxValue" placeholder="单位元，0表示不限制" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>							
							<el-col class="mb20">
								<el-form-item label="超过多少金额需要强制审核" prop="cashOutAmountOverflowValue">
									<el-input v-model="state.ruleForm.cashOutAmountOverflowAuditValue" placeholder="单位元" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>	

                        <el-col>
                            <el-form-item>
                                <el-button type="primary"  @click="onSubmit">保 存</el-button>
                            </el-form-item>
						</el-col>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script lang="ts" name="orderSet" setup>
import { reactive, ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { ConfigApi } from '/@/api/config/index';

const myRefForm = ref();
const configApi = ConfigApi();
const state = reactive({
	fit: "fill",
	ruleForm: {
		enableAutoCancelOrder: false,
		unPayOrderCancelTime: 0,
		enableAmountCashOutAudit: true,
		cashOutAmountOverflowAuditValue: 500,
		cashOutAmountDayMaxTimeValue:3,
		cashOutAmountDayMaxValue:1000,
		cashOutAmountDayBalanceMaxValue: 0,
	} as any,
	rules: {
		"unPayOrderCancelTime": [
			{required: true, message: '请输入订单取消时间', trigger: 'blur'},
		],
		"cashOutAmountOverflowAuditValue": [
			{required: true, message: '请输入提现超出金额', trigger: 'blur'},
		],
		"cashOutAmountDayMaxTimeValue": [
			{required: true, message: '请输入单日提现最大次数', trigger: 'blur'},
		],
		"cashOutAmountDayMaxValue": [
			{required: true, message: '请输入单次提现最大金额', trigger: 'blur'},
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
