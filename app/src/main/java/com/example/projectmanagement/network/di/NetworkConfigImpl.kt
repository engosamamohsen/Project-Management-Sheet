package com.example.projectmanagement.network.di


import com.example.projectmanagement.BuildConfig
import com.example.projectmanagement.network.config.NetworkConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConfigImpl @Inject constructor() : NetworkConfig {
    override val spreadsheetId: String = BuildConfig.EXCEL_API_KEY
    override val serviceAccountType: String = BuildConfig.SERVICE_ACCOUNT_TYPE
    override val serviceAccountProjectId: String = BuildConfig.SERVICE_ACCOUNT_PROJECT_ID
    override val serviceAccountPrivateKeyId: String = BuildConfig.SERVICE_ACCOUNT_PRIVATE_KEY_ID
    override val serviceAccountPrivateKey: String = BuildConfig.SERVICE_ACCOUNT_PRIVATE_KEY
    override val serviceAccountClientEmail: String = BuildConfig.SERVICE_ACCOUNT_CLIENT_EMAIL
    override val serviceAccountClientId: String = BuildConfig.SERVICE_ACCOUNT_CLIENT_ID
    override val serviceAccountAuthUri: String = BuildConfig.SERVICE_ACCOUNT_AUTH_URI
    override val serviceAccountTokenUri: String = BuildConfig.SERVICE_ACCOUNT_TOKEN_URI
    override val serviceAccountAuthProviderCertUrl: String = BuildConfig.SERVICE_ACCOUNT_AUTH_PROVIDER_CERT_URL
    override val serviceAccountClientCertUrl: String = BuildConfig.SERVICE_ACCOUNT_CLIENT_CERT_URL
}