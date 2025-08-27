package com.example.projectmanagement.network.config

interface NetworkConfig {
    val spreadsheetId: String
    val serviceAccountType: String
    val serviceAccountProjectId: String
    val serviceAccountPrivateKeyId: String
    val serviceAccountPrivateKey: String
    val serviceAccountClientEmail: String
    val serviceAccountClientId: String
    val serviceAccountAuthUri: String
    val serviceAccountTokenUri: String
    val serviceAccountAuthProviderCertUrl: String
    val serviceAccountClientCertUrl: String
}