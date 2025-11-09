package com.gaoyifeng.aioserver.infrastructure.gateway;

import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinQrCodeRequestDTO;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinQrCodeResponseDTO;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinTemplateMessageDTO;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinTokenResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 微信API网关接口 - Infrastructure层
 * 使用Retrofit2实现微信API调用
 * 参考study项目IWeixinApiService实现
 */
public interface IWeixinApiGateway {

    /**
     * 获取 Access token
     * 文档：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     *
     * @param grantType 获取access_token填写client_credential
     * @param appId     第三方用户唯一凭证
     * @param appSecret 第三方用户唯一凭证密钥，即appsecret
     * @return 响应结果
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenResponseDTO> getToken(@Query("grant_type") String grantType,
                                         @Query("appid") String appId,
                                         @Query("secret") String appSecret);

    /**
     * 获取凭据 ticket
     * 文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
     * 前端根据凭证展示二维码：https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET
     *
     * @param accessToken            getToken 获取的 token 信息
     * @param weixinQrCodeRequestDTO 入参对象
     * @return 应答结果
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeResponseDTO> createQrCode(@Query("access_token") String accessToken,
                                               @Body WeixinQrCodeRequestDTO weixinQrCodeRequestDTO);

    /**
     * 发送微信公众号模板消息
     * 文档：https://mp.weixin.qq.com/debug/cgi-bin/readtmpl?t=tmplmsg/faq_tmpl
     *
     * @param accessToken              getToken 获取的 token 信息
     * @param weixinTemplateMessageDTO 入参对象
     * @return 应答结果
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(@Query("access_token") String accessToken,
                          @Body WeixinTemplateMessageDTO weixinTemplateMessageDTO);

}