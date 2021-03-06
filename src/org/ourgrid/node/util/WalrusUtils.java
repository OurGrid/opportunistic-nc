package org.ourgrid.node.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.ourgrid.node.model.bundle.BundleTask;

public class WalrusUtils {

	private static final Logger LOGGER = Logger.getLogger(WalrusUtils.class);
	
	private static final String XML_FORMAT = ".xml";
	private static final String MANIFEST_XML_FILE_NAME = "manifest";
	private static final String UPDATED_MANIFEST_XML_FILE_NAME = "updated-";
	private static final String IMAGE_DIGEST_XPATH = "//image/digest";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
	private static final String VERB = "GET";
	private static final int CERT_LINE_LENGTH = 65;
	
	private static final String GET_DECRYPTED_IMG = "GetDecryptedImage";
	private static final String GET_OBJECT = "GetObject";
	private static final String NC_CHECK_BUCKET = "./euca-check-bucket";
	private static final String NC_BUNDLE_UPLOAD = "./euca-bundle-upload";
	private static final String NC_DELETE_BUNDLE = "./euca-delete-bundle";
	
	public static final String IMG_EXT = ".img";

	public static void downloadImage(String resourceURLStr, String imageId, 
			Properties properties) throws Exception {
		
		String imageFilePath = getImagePath(imageId, properties);
		File imageFile = new File(imageFilePath);
		if (!imageFile.getParentFile().exists()) {
			imageFile.getParentFile().mkdirs();
		}
		
		if (!imageFile.exists() || !isImgDigestOK(resourceURLStr, imageId, imageFile, properties)) {
			downloadImageResources(resourceURLStr, properties, imageFilePath, imageId);
		}
	}

	private static void downloadImageResources(String resourceURLStr, Properties properties,
			String imageFilePath, String imageId) throws Exception {
		
		HttpResponse imageResponse = performWalrusHTTPRequest(resourceURLStr, properties, 
				GET_DECRYPTED_IMG);
		downloadResourceToFile(imageResponse.getEntity(), imageFilePath);
		
		HttpResponse imageXMLResponse = performWalrusHTTPRequest(resourceURLStr,
				properties, GET_OBJECT);
		downloadResourceToFile(imageXMLResponse.getEntity(), getImageXMLPath(imageId, properties, 
				false));
	}

	private static boolean isImgDigestOK(String resourceURLStr, String imageId, File imageFile, 
			Properties properties) throws Exception {
		
		HttpResponse newImageDigestResponse = performWalrusHTTPRequest(resourceURLStr,
				properties, GET_OBJECT);
		
		String newImageXMLPath = getImageXMLPath(imageId, properties, true);
		
		downloadResourceToFile(newImageDigestResponse.getEntity(), 
				newImageXMLPath);
		
		String newImageDigest = getXMLImageDigest(new File(newImageXMLPath));

		File imgManifestFile = new File(getImageXMLPath(imageId, properties, false));
		
		String oldImageDigest = getXMLImageDigest(imgManifestFile);
		
		return oldImageDigest.equals(newImageDigest);
	}

	private static String getXMLImageDigest(File imgManifestFile)
			throws DocumentException, FileNotFoundException {
		SAXReader reader = new SAXReader();
        Document imgManifestDoc = reader.read(new FileInputStream(imgManifestFile));
        Node digest = imgManifestDoc.selectSingleNode(IMAGE_DIGEST_XPATH);
        String imageDigest = digest.getText();
		return imageDigest;
	}

	private static File downloadResourceToFile(HttpEntity entity, String filePath)
			throws FileNotFoundException, IOException {
		
		File imgManifestFile = new File(filePath);
		FileOutputStream fos = new FileOutputStream(imgManifestFile);
		entity.writeTo(fos);
		fos.close();
		return imgManifestFile;
	}

	private static HttpResponse performWalrusHTTPRequest(String resourceURLStr,
			Properties properties, String eucaOperation) throws URISyntaxException,
			UnsupportedEncodingException, SignatureException, IOException,
			FileNotFoundException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException,
			InvalidKeyException, ClientProtocolException {
		
		URI resourceURL = new URI(resourceURLStr);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(resourceURL);

		String dateStr = DATE_FORMAT.format(new Date());

		httpGet.addHeader("Authorization", "Euca");
		httpGet.addHeader("Date", dateStr);
		httpGet.addHeader("EucaOperation", eucaOperation);
		httpGet.addHeader("EucaCert", getNodeCertBase64(properties));
		httpGet.addHeader("EucaSignature", getBase64Signature(resourceURL, 
				dateStr, properties));

		HttpResponse response = httpClient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("Could not fetch XML");
		}
		return response;
	}

	private static String getBase64Signature(URI resourceURL, String dateStr, Properties properties)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException,
			UnsupportedEncodingException {

		String privateKeyAlias = properties.getProperty(NodeProperties.PRIVATEKEY_ALIAS);
		String privateKeyPass = properties.getProperty(NodeProperties.PRIVATEKEY_PASS);
		KeyStore keyStore = AuthUtils.getKeyStore(properties);
		PrivateKey key = (PrivateKey) keyStore.getKey(privateKeyAlias, privateKeyPass.toCharArray());
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(key);

		StringBuilder signatureBuilder = new StringBuilder(); 
		signatureBuilder.append(VERB).append("\n");
		signatureBuilder.append(dateStr).append("\n");
		signatureBuilder.append(resourceURL.getPath()).append("\n");
		signature.update(signatureBuilder.toString().getBytes("UTF-8"));

		return CodecUtils.encodeBase64(signature.sign());
	}

	private static String getNodeCertBase64(Properties properties)
			throws UnsupportedEncodingException, SignatureException,
			IOException, FileNotFoundException, KeyStoreException, NoSuchAlgorithmException, CertificateException {

		KeyStore keyStore = AuthUtils.getKeyStore(properties);
		Certificate certificate = keyStore.getCertificate(properties.getProperty(NodeProperties.NODECERT_ALIAS));
		sun.security.x509.X509CertImpl x509Cert = (sun.security.x509.X509CertImpl)certificate;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("-----BEGIN CERTIFICATE-----\n");
		String encodedCert = CodecUtils.encodeBase64(x509Cert.getEncoded());
		for (int i = 0; i < encodedCert.length(); i += CERT_LINE_LENGTH - 1) {
			int endIndex = Math.min(i + CERT_LINE_LENGTH - 1, encodedCert.length());
			String encodedCertLine = encodedCert.substring(i, endIndex);
			strBuilder.append(encodedCertLine).append("\n");
		}
		strBuilder.append("-----END CERTIFICATE-----");
		return CodecUtils.encodeBase64(strBuilder.toString().getBytes("UTF-8"));
	}
	
	public static String getImagePath(String machineImageId, Properties properties) {
		return getImageFolderPath(machineImageId, properties) + File.separator + 
				machineImageId + ".img";
	}
	
	private static String getImageXMLPath(String machineImageId, Properties properties, 
			boolean xmlCheck) {
		return getImageFolderPath(machineImageId, properties) + File.separator + 
				(xmlCheck? UPDATED_MANIFEST_XML_FILE_NAME : 
						"") + MANIFEST_XML_FILE_NAME + XML_FORMAT;
	}
	
	private static String getImageFolderPath(String machineImageId, Properties properties) {
		return properties.getProperty(NodeProperties.CACHEROOT) + File.separator + machineImageId;
	}

	public static void bundleUpload(BundleTask bt, String imagePath, Properties properties) 
					throws Exception {
		
		String eucaCmdPath = properties.getProperty(NodeProperties.EUCA2OOLS_LOCATION_HOME);
		ProcessBuilder processBuilder = new ProcessBuilder().directory(new File(eucaCmdPath));
		
		processBuilder.command(NC_BUNDLE_UPLOAD, 
				"-b", bt.getBucketName(), 
				"-i", imagePath, 
				"-U", bt.getWalrusURL(), 
				"-c", bt.getS3Policy(), 
				"--policysignature", bt.getS3PolicySig(), 
				"--euca-auth");
		
		configureEnv(properties, processBuilder, bt.getUserPublicKey());
		
		LOGGER.info("About to run cmd: " + processBuilder.command());
		
		Process process = processBuilder.start();
		bt.setCurrentProcess(process);
		int exitValue = process.waitFor();
		String cmdOutput = IOUtils.toString(process.getInputStream());
		LOGGER.info("Bundle cmd output: " + cmdOutput);
		String cmdStderr = IOUtils.toString(process.getErrorStream());
		LOGGER.info("Bundle cmd stderr: " + cmdStderr);
		
		if (exitValue != 0) {
			throw new RuntimeException("Bundle upload: Could not upload bundle!");
		}
	}

	public static boolean checkBucket(BundleTask bt, Properties properties) throws Exception {
		String eucaCmdPath = properties.getProperty(NodeProperties.EUCA2OOLS_LOCATION_HOME);
		ProcessBuilder processBuilder = new ProcessBuilder().directory(new File(eucaCmdPath));
		
		processBuilder.command(NC_CHECK_BUCKET, 
				"-U", bt.getWalrusURL(),
				"-b", bt.getBucketName(), 
				"--euca-auth");
		
		configureEnv(properties, processBuilder, bt.getUserPublicKey());
		
		LOGGER.info("About to run cmd: " + processBuilder.command());
		
		Process process = processBuilder.start();
		bt.setCurrentProcess(process);
		int exitValue = process.waitFor();
		String cmdOutput = IOUtils.toString(process.getInputStream());
		LOGGER.info("Check bucket cmd output: " + cmdOutput);
		String cmdStderr = IOUtils.toString(process.getErrorStream());
		LOGGER.info("Check bucket cmd stderr: " + cmdStderr);
		
		if (exitValue != 0) {
			return false;
		}
		
		return true;
	}
	
	public static void deleteBundle(BundleTask bt, Properties properties) throws Exception {
		String eucaCmdPath = properties.getProperty(NodeProperties.EUCA2OOLS_LOCATION_HOME);
		ProcessBuilder processBuilder = new ProcessBuilder().directory(new File(eucaCmdPath));
		
		String[] deleteArgs = new String[] {
				NC_DELETE_BUNDLE, 
				"-U", bt.getWalrusURL(),
				"-b", bt.getBucketName(),
				"-p", bt.getFilePrefix(),
				"--euca-auth"
		};
		List<String> deleteArgsList = new ArrayList<String>(Arrays.asList(deleteArgs));
		
		if (!bt.isBundleBucketExists()) {
			deleteArgsList.add("--clear");
		}
		
		processBuilder.command(deleteArgsList);
		
		configureEnv(properties, processBuilder, bt.getUserPublicKey());
		
		LOGGER.info("About to run cmd: " + processBuilder.command());
		Process process = processBuilder.start();
		bt.setCurrentProcess(process);
		int exitValue = process.waitFor();
		String cmdOutput = IOUtils.toString(process.getInputStream());
		LOGGER.info("Delete bundle cmd output: " + cmdOutput);
		String cmdStderr = IOUtils.toString(process.getErrorStream());
		LOGGER.info("Delete bundle cmd stderr: " + cmdStderr);
		
		if (exitValue != 0) {
			throw new RuntimeException("Delete bundle: Could not delete bundle!");
		}
	}

	private static void configureEnv(Properties properties,
			ProcessBuilder processBuilder, String userPublicKey) throws Exception {
		File nodeCertPEM = AuthUtils.getCertPEMFile(properties.getProperty(
				NodeProperties.PRIVATEKEY_ALIAS), properties);
		File nodePrivateKeyPEM = AuthUtils.getPrivateKeyPEMFile(
				properties.getProperty(NodeProperties.PRIVATEKEY_ALIAS), 
				properties.getProperty(NodeProperties.PRIVATEKEY_PASS), properties);
		File cloudCertPEM = AuthUtils.getCertPEMFile(properties.getProperty(
				NodeProperties.CLOUDKEY_ALIAS), properties);
		
		Map<String, String> env = processBuilder.environment();
		env.put("EC2_CERT", nodeCertPEM.getAbsolutePath());
		env.put("EUCA_CERT", nodeCertPEM.getAbsolutePath());
		env.put("EUCALYPTUS_CERT", cloudCertPEM.getAbsolutePath());
		env.put("EUCA_PRIVATE_KEY", nodePrivateKeyPEM.getAbsolutePath());
		env.put("EC2_USER_ID", "123456789012");
		env.put("EC2_ACCESS_KEY", userPublicKey);
		env.put("EC2_SECRET_KEY", "IGNORED");
	}
}
