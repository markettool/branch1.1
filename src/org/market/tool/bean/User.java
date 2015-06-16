package org.market.tool.bean;

import java.util.List;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

/** ����BmobChatUser����������������Ҫ���ӵ����Կ��ڴ����
  * @ClassName: TextUser
  * @Description: TODO
  * @author smile
  * @date 2014-5-29 ����6:15:45
  */
public class User extends BmobChatUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �����Ĳ����б�
	 */
	private BmobRelation blogs;
	
	/**
	 * //��ʾ����ƴ��������ĸ
	 */
	private String sortLetters;
	
	/**
	 * //�Ա�-true-��
	 */
	private Boolean sex;
	
	private Blog blog;
	
	private String mysign;
	
	/**
	 * ��������
	 */
	private BmobGeoPoint location;//
	
//	private Integer hight;
//    private List<BmobFile> bmobFiles;
    
    private String phonenum;
	
//	public List<BmobFile> getBmobFiles() {
//		return bmobFiles;
//	}
//	public void setBmobFiles(List<BmobFile> bmobFiles) {
//		this.bmobFiles = bmobFiles;
//	}
	
	public double getFund() {
		return fund;
	}
	public void setFund(double fund) {
		this.fund = fund;
	}
	private double fund;
	
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
//	public Integer getHight() {
//		return hight;
//	}
//	public void setHight(Integer hight) {
//		this.hight = hight;
//	}
	public BmobRelation getBlogs() {
		return blogs;
	}
	public void setBlogs(BmobRelation blogs) {
		this.blogs = blogs;
	}
	public BmobGeoPoint getLocation() {
		return location;
	}
	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getMysign() {
		return mysign;
	}
	public void setMysign(String mysign) {
		this.mysign = mysign;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
}
