package io.graphenee.sms.impl;

import io.graphenee.sms.api.GxSmsService;
import io.graphenee.sms.proto.EoceanSmsConfigProtos;
import io.graphenee.sms.proto.EoceanSmsConfigProtos.ConfigMessage;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EoceanSmsServiceImpl implements GxSmsService {

	private ConfigMessage configMessage;
	private EoceanService eoceanService;

	public EoceanSmsServiceImpl(EoceanSmsConfigProtos.ConfigMessage configMessage) {
		this.configMessage = configMessage;
		eoceanService = new Retrofit.Builder().baseUrl(configMessage.getBaseUrl()).build().create(EoceanService.class);
	}

	@Override
	public String sendTransactionalMessage(String phone, String message) {
		throw new UnsupportedOperationException("Please use method with senderId.");
	}

	@Override
	public String sendPromotionalMessage(String phone, String message) {
		throw new UnsupportedOperationException("Please use method with senderId.");
	}

	@Override
	public String sendTransactionalMessage(String senderId, String phone, String message) {
		return sendMessage(senderId, phone, message);
	}

	@Override
	public String sendPromotionalMessage(String senderId, String phone, String message) {
		return sendMessage(senderId, phone, message);
	}

	private String sendMessage(String senderId, String phone, String message) {
		Call<Void> call = eoceanService.requestAPI(configMessage.getUser(), configMessage.getPwd(), senderId, phone, message);
		try {
			Response<Void> response = call.execute();
			if (response.isSuccessful())
				return "SMS Sent";
			return response.message();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}