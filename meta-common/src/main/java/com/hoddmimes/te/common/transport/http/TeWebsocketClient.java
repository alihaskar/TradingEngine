/*
 * Copyright (c)  Hoddmimes Solution AB 2021.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hoddmimes.te.common.transport.http;







import javax.net.ssl.*;

import javax.websocket.*;
import java.net.Socket;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@ClientEndpoint
public class TeWebsocketClient extends Endpoint implements MessageHandler.Whole<String> {
	public interface WssCallback {
		public void onOpen(Session session, EndpointConfig config);
		public void onMessage( String pBdxMsg );
		public void onClose(Session session, CloseReason closeReason);
		public void onError(Session session, Throwable throwable);
	}

	private Session mSession;
	private WssCallback mCallback;


	public TeWebsocketClient(String endpointURI, String pAuthId, WssCallback pCallback ) {
		try {
			mCallback = pCallback;

			WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();

			ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {};
			ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().configurator(configurator).build();
			clientEndpointConfig.getUserProperties().put("org.apache.tomcat.websocket.SSL_CONTEXT", createSSLContext());

			Session session = webSocketContainer.connectToServer( this, clientEndpointConfig, new URI(endpointURI + "?authid=" + pAuthId));
			session.addMessageHandler( this );

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void sendMessage( String pMessage ) {
		mSession.getAsyncRemote().sendText( pMessage );
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		mSession = session;
		mCallback.onOpen( session, config);
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		mCallback.onClose( session, closeReason );
	}

	@Override
	public void onError(Session session, Throwable throwable) {
		mCallback.onError( session, throwable );
	}

	@Override
	public void onMessage(String message) {
		mCallback.onMessage( message );
	}

	private SSLContext createSSLContext() {
		SSLContext tSSLContext = null;
		try {
			tSSLContext = SSLContext.getInstance("TLS");
			TrustManager[] tTrustMgrs = new TrustManager[]{new BlindTrustManager()};
			tSSLContext.init(null, tTrustMgrs, new SecureRandom());


			SSLSessionContext tSSLSessionContext = tSSLContext.getClientSessionContext();
			tSSLSessionContext.setSessionTimeout(0);
			tSSLSessionContext.setSessionCacheSize(0);
			SSLParameters tSSLParams = tSSLContext.getDefaultSSLParameters();
			tSSLParams.setNeedClientAuth(false);

			tSSLContext.getDefaultSSLParameters().setEndpointIdentificationAlgorithm(null);
			tSSLContext.getDefaultSSLParameters().setWantClientAuth(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tSSLContext;


	}




	class BlindTrustManager extends X509ExtendedTrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			System.out.println("checkClientTrusted");
		}
		@Override
		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			System.out.println("checkServerTrusted");
		}
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			System.out.println("getAcceptedIssuers");
			return new X509Certificate[0];
		}

		@Override
		public void checkClientTrusted(X509Certificate[] pX509Certificates, String pS, Socket pSocket) throws CertificateException {
			System.out.println("checkClientTrusted");
		}

		@Override
		public void checkServerTrusted(X509Certificate[] pX509Certificates, String pS, Socket pSocket) throws CertificateException {
			System.out.println("checkServerTrusted");
		}

		@Override
		public void checkClientTrusted(X509Certificate[] pX509Certificates, String pS, SSLEngine pSSLEngine) throws CertificateException {
			System.out.println("checkServerTrusted");
		}

		@Override
		public void checkServerTrusted(X509Certificate[] pX509Certificates, String pS, SSLEngine pSSLEngine) throws CertificateException {

		}
	}

}
