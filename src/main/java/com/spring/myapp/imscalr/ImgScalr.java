package com.spring.myapp.imscalr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.imgscalr.Scalr.Rotation;

public class ImgScalr {

	/**
	* 주어진 BufferedImage 객체를 file로 기록
	* 
	* @param bufferedImage BufferedImage 객체
	* @param resultFile BufferedImage 객체가 기록될 file
	* @param quality JPEG 압축률(0.0f ~ 1.0f)
	* 
	* @throws IOException
	*/
	public void writeJpegImage(BufferedImage bufferedImage, File resultFile, float quality) throws IOException {
	ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(resultFile);
	Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpeg");

	if (iterator.hasNext() == false) {
	System.out.println("# ImageWriter not available.");
	return;
	}

	ImageWriter imageWriter = iterator.next();

	ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
	imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	imageWriteParam.setCompressionQuality(quality);

	imageWriter.setOutput(imageOutputStream);
	imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
	imageWriter.dispose();
	}

	/**
	* 이미지 resize
	* 
	* @param sourceFile 원본 이미지
	* @param resultFile 결과 이미지
	* @param width 넓이(단위: pixel)
	* @param height 높이(단위: pixel)
	* 
	* @throws IOException
	*/
	public void resize(File sourceFile, File resultFile, int width, int height) throws IOException {
	BufferedImage bufferedImage = ImageIO.read(sourceFile);
	BufferedImage doneImage = Scalr.resize(bufferedImage, Mode.AUTOMATIC, width, height, Scalr.OP_ANTIALIAS);

	writeJpegImage(doneImage, resultFile, 0.75f);
	}

	/**
	* 이미지 rotate. 시계 방향으로 회전.
	* 회전 정도를 따로 지정할 수 없음.<br/>
	* Rotation 객체는 Enum 객체라 Rotation 객체에 정해진 것만 사용 가능
	* 
	* @param sourceFile 원본 이미지
	* @param resultFile 결과 이미지
	* @param rotation Enum 객체인 Ratation 객체. Rotation.CW_90, Rotation.CW_180, Rotation.CW_270
	* 
	* @throws IOException
	*/
	public void rotate(File sourceFile, File resultFile, Rotation rotation) throws IOException {
	BufferedImage bufferedImage = ImageIO.read(sourceFile);
	BufferedImage doneImage = Scalr.rotate(bufferedImage, rotation, Scalr.OP_ANTIALIAS);

	writeJpegImage(doneImage, resultFile, 0.75f);
	}

	/**
	* 이미지 crop
	* 
	* @param sourceFile 원본 이미지
	* @param resultFile 결과 이미지
	* @param x crop 시작 X 좌표
	* @param y crop 시작 Y 좌표
	* @param width 넓이(단위: pixel)
	* @param height 높이(단위: pixel)
	* @throws IOException
	*/
	public void crop(File sourceFile, File resultFile, int x, int y, int width, int height) throws IOException {
	BufferedImage bufferedImage = ImageIO.read(sourceFile);
	BufferedImage doneImage = Scalr.crop(bufferedImage, x, y, width, height, Scalr.OP_ANTIALIAS);

	writeJpegImage(doneImage, resultFile, 0.75f);
	}

	/**
	* 이미지 좌우반전. method 명은 Image Magick 기준
	* 
	* @param sourceFile 원본 이미지
	* @param resultFile 결과 이미지
	* 
	* @throws IOException
	*/
	public void flop(File sourceFile, File resultFile) throws IOException {
	BufferedImage sourceBufferedImage = ImageIO.read(sourceFile);
	BufferedImage doneImage = Scalr.rotate(sourceBufferedImage, Rotation.FLIP_HORZ, Scalr.OP_ANTIALIAS);

	writeJpegImage(doneImage, resultFile, 0.75f);
	}

	/**
	* 이미지 상하반전. method 명은 Image Magick 기준
	* 
	* @param sourceFile 원본 이미지
	* @param resultFile 결과 이미지
	* 
	* @throws IOException
	*/
	public void flip(File sourceFile, File resultFile) throws IOException {
	BufferedImage sourceBufferedImage = ImageIO.read(sourceFile);
	BufferedImage doneImage = Scalr.rotate(sourceBufferedImage, Rotation.FLIP_VERT, Scalr.OP_ANTIALIAS);

	writeJpegImage(doneImage, resultFile, 0.75f);
	}
	}
