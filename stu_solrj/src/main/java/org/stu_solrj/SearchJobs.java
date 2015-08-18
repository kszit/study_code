package org.stu_solrj;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
&nbsp;* @Project JobsOtherWebSearch
&nbsp;* @Package com.fjsh.SearchJobsFirst
&nbsp;* @ClassName: SearchJobs&nbsp;
&nbsp;* @Author fjsh
&nbsp;* @Description: ��solr�еĸ������ܽ��в���
&nbsp;* @Date 2014-3-5 ����3:38:09&nbsp;
&nbsp;*/

public class SearchJobs {
	private static String url = "jdbc:sqlserver://192.168.2.106:1433;DatabaseName=JobsOtherweb51jobDB";
	private static String user = "sa";
	private static String password = "sa";
	private String Corenum;
	public static int JobsId = 219443;// start jobsid
	public HttpSolrClient solrServer = null;// new
										// HttpSolrServer("http://192.168.2.100:8080/solr/JobsOtherWeb1");

	// 1�� ����solrserver����
	public HttpSolrClient createSolrServer() {
		HttpSolrServer solr = null;
		try {
			solr = new HttpSolrServer(
					"http://172.31.131.205:8983/solr/myfirstcore");
			solr.setConnectionTimeout(100);
			solr.setDefaultMaxConnectionsPerHost(100);
			solr.setMaxTotalConnections(100);
		} catch (Exception e) {
			System.out.println("����tomcat��������˿��Ƿ���!");
			e.printStackTrace();
		}
		return solr;
	}

	// �򵥵Ĳ�ѯ��ȡ����ʮ��
	public void querytop20() throws IOException {
		solrServer = createSolrServer();
		System.out.println("�򵥲�ѯȡ��ǰ��ʮ��");
		String dtStart = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		System.out.println("��ʼʱ�䣺" + dtStart + "\n");
		try {
			SolrQuery query = new SolrQuery();// ��ѯ
			query.setQuery("*:*");
			query.setRows(20);
			SolrDocumentList docs = solrServer.query(query).getResults();
			for (SolrDocument sd : docs) {
				System.out.println(sd.getFieldValue("name"));
				System.out.println(sd.getFieldValue("publishDate"));
			}
			solrServer.shutdown();
			String dtEnd = new SimpleDateFormat("yyyyMMddHHmmssSSS")
					.format(new Date());
			System.out.println(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] a) throws IOException{
		SearchJobs search = new SearchJobs();
		
		search.querytop20();
	}

	// ɾ������
	// �ݲ�ѯ���ɾ����
	public void DeleteByQuery() {
		solrServer = createSolrServer();
		try {
			// ɾ�����е�����
			solrServer.deleteByQuery("jobsName:�߼�����֧��");
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����������ɾ��������
	public void DeleteByQueryJobsId() {
		solrServer = createSolrServer();
		try {
			solrServer.deleteById("515792");
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ѯ
	// SolrJ�ṩ�Ĳ�ѯ���ܱȽ�ǿ�󣬿��Խ��н���в�ѯ����Χ��ѯ������ȡ�
	// ����һ�·�Χ��ѯ�ĸ�ʽ��[star t TO end]��start��end����Ӧ���ݸ�ʽ��ֵ���ַ�����ʽ����TO�� һ��Ҫ���ִ�д��
	/*
	 * field ��ѯ���ֶ��������� key ��ѯ���ֶ����ƶ�Ӧ��ֵ start ��ѯ����ʼλ�� count һ�β�ѯ���������� sortfield
	 * ��Ҫ������ֶ����� flag ��Ҫ������ֶε�����ʽ���Ϊtrue ���� ���Ϊfalse ���� hightlight �Ƿ���Ҫ������ʾ
	 */
	public QueryResponse Search(String[] field, String[] key, int start,
			int count, String[] sortfield, Boolean[] flag, Boolean hightlight) {
		solrServer = createSolrServer();
		// ��������Ƿ�Ϸ�
		if (null == field || null == key || field.length != key.length) {
			return null;
		}
		if (null == sortfield || null == flag
				|| sortfield.length != flag.length) {
			return null;
		}

		SolrQuery query = null;
		try {
			// ��ʼ����ѯ����
			query = new SolrQuery(field[0] + ":" + key[0]);
			for (int i = 0; i < field.length; i++) {
				query.addFilterQuery(field[i] + ":" + key[i]);
			}
			// ������ʼλ���뷵�ؽ����
			query.setStart(start);
			query.setRows(count);
			// ��������
			for (int i = 0; i < sortfield.length; i++) {
				if (flag[i]) {
					query.addSortField(sortfield[i], SolrQuery.ORDER.asc);
				} else {
					query.addSortField(sortfield[i], SolrQuery.ORDER.desc);
				}
			}
			// ���ø���
			if (null != hightlight) {
				query.setHighlight(true); // �����������
				query.addHighlightField("jobsName");// �����ֶ�
				query.setHighlightSimplePre("<font color=\"red\">");// ���
				query.setHighlightSimplePost("</font>");
				query.setHighlightSnippets(1);// �����Ƭ����Ĭ��Ϊ1
				query.setHighlightFragsize(1000);// ÿ����Ƭ����󳤶ȣ�Ĭ��Ϊ100

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		QueryResponse rsp = null;
		try {
			rsp = solrServer.query(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// ���ز�ѯ���
		return rsp;
	}

	// Facet��һ��Ӧ�ã��Զ���ȫ
	// prefixΪǰ׺��minΪ��󷵻ؽ����
	// field��Ҫ��ѯ�����ز�ȫ���ֶΣ�prefix��Ҫ��ѯ�����ص��ֶβ�ȫֵ

	public String[] autoComplete(String field, String prefix, int min) {
		/*------------��һ�����------------------------*/
		solrServer = createSolrServer();
		String words[] = null;
		StringBuffer sb = new StringBuffer("");
		SolrQuery query = new SolrQuery(field + ":" + prefix);
		QueryResponse rsp = new QueryResponse();
		// FacetΪsolr�еĲ�η����ѯ
		/*------------�ڶ�����ǣ�����ӵ�һ�����ִ�е�������Ҫ300ms���Խ�����Ĵ������ʵ�������------------------------*/
		try {
			query.setFacet(true);
			// query.setQuery("*:*");
			query = new SolrQuery(field + ":" + prefix);
			query.setFacetPrefix(prefix);
			query.addFacetField(field);
			rsp = solrServer.query(query);
			/*------------��������ǣ�����ӵڶ������ִ�е�������Ҫ200ms���˴������ٽ����Ż������ڲ�ѯ�ĸ�����------------------------*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		if (null != rsp) {
			FacetField ff = rsp.getFacetField(field);
			List<Count> countList = ff.getValues();
			if (null == countList) {
				return null;
			}
			for (int i = 0; i < countList.size(); i++) {
				String tmp[] = countList.get(i).toString().split(" ");
				// �ų�������
				if (tmp[0].length() < 2) {
					continue;
				}
				sb.append(tmp[0] + " ");
				min--;
				if (min == 0) {
					break;
				}
			}
			words = sb.toString().split(" ");
		} else {
			return null;
		}
		return words;
	}
	/**
	 * @Author fjsh
	 * @Title SearchGroup
	 * @Description ��group���в���
	 * @param QUERY_CONTENT ��ѯ����
	 * @param QUERY_ROWS ���ҵ�����,Ĭ����10
	 * @param GROUP true or false �Ƿ�group��ѯ
	 * @param GROUP_FIELD ��ѯfield
	 * @param GROUP_LIMIT The number of results (documents) to return for each group. Defaults to 1
	 * @Return void
	 * @Throws 
	 * @Date 2014-5-7
	 * ��������ʱ�����ڶ������������û�����ܺ��ǵ�������ʾ�Ľ���������룬�����˷���������Ϊ�ο�
	 */
	public void SearchGroup(String QUERY_CONTENT,int QUERY_ROWS, Boolean GROUP, String GROUP_FIELD,String GROUP_LIMIT) {
		 SolrServer server = createSolrServer();  
		 SolrQuery param = new SolrQuery();  
		 param.setQuery("jobsName:"+QUERY_CONTENT);  
		 param.setRows(QUERY_ROWS);  
		 param.setParam(GroupParams.GROUP, GROUP);  
		 param.setParam(GroupParams.GROUP_FIELD, GROUP_FIELD);  		
		 param.setParam(GroupParams.GROUP_LIMIT, GROUP_LIMIT);  
		 QueryResponse response = null;  
		 try {  
		     response = server.query(param);  
		 } catch (SolrServerException e) {  
		    // logger.error(e.getMessage(), e);  
		 }  
		 Map<String, Integer> info = new HashMap<String, Integer>();  
		 GroupResponse groupResponse = response.getGroupResponse();  
		 if(groupResponse != null) {  
		     List<GroupCommand> groupList = groupResponse.getValues();  
		     for(GroupCommand groupCommand : groupList) {  
		         List<Group> groups = groupCommand.getValues();  
		         for(Group group : groups) {  
		             info.put(group.getGroupValue(), (int)group.getResult().getNumFound()); 
		             System.out.println(group.getGroupValue()+"---"+group.getResult().getNumFound());
		         }  
		     }  
		 }  
	}

	/*
	 * ������һ��facet֮����˵˵��ôʵ��facet��facet��ʵ����ʵ�ܼ򵥣���Ҫ�����������ϴ��Ͼ�OK��
	 * 
	 * facet=on/true #������facet facet.field=cate #����Ҫͳ�Ƶ��棨���飩����������ķ��࣬Ʒ�ƣ����Զ�γ���
	 * facet.limit =20 #ÿ��������෵������ facet.mincount = 1 #�����ʾ������ĳһ��Ŀ����С������
	 * facet.missing = on/true #ͳ��null��ֵ facet.method = #Ĭ��Ϊfc, fc��ʾField Cache
	 * ����
	 * ��http://localhost/product/select/?q=������&facet=on&facet.field=category&facet
	 * .field=brand&facet.mincount=1����������з���xml��facet���
	 * 
	 * 
	 * view sourceprint? 01 <lst name="facet_counts"> 02 <lst
	 * name="facet_queries"/> 03 <lst name="facet_fields"> 04 <lst
	 * name="category"> 05 <int name="2742">64</int> 06 <int name="793">48</int>
	 * 07 <int name="2741">12</int> 08 <int name="801">6</int> 09 <int
	 * name="1087">1</int> 10 </lst> 11 <lst name="brand"> 12 <int
	 * name="229">74</int> 13 <int name="227">16</int> 14 <int
	 * name="270">13</int> 15 <int name="317">10</int> 16 <int name="0">4</int>
	 * 17 <int name="165">4</int> 18 <int name="203">3</int> 19 <int
	 * name="147">2</int> 20 <int name="166">2</int> 21 <int name="217">1</int>
	 * 22 <int name="342">1</int> 23 <int name="343">1</int> 24 </lst> 25 </lst>
	 * <lst name="category"> ������ <int name="2742">64</int>
	 * ��������Ŀ��name��ʾ��Ŀ��64��ͳ�ƽ������
	 * 
	 * 
	 * 
	 * 
	 * Date Facet �������͵��ֶ����ĵ��кܳ��� , ����Ʒ����ʱ�� , �������ʱ�� , �鼮�ϼ�ʱ��ȵ� . ĳЩ�������Ҫ�����Щ�ֶν���
	 * Facet. ����ʱ���ֶε�ȡֵ�������� , �û��������ĵĲ���ĳ��ʱ������ĳ��ʱ����ڵĲ�ѯͳ�ƽ�� . Solr
	 * Ϊ�����ֶ��ṩ�˸�Ϊ����Ĳ�ѯͳ�Ʒ�ʽ . ��Ȼ , �ֶε����ͱ����� DateField( ���������� ). ��Ҫע����� , ʹ�� Date
	 * Facet ʱ , �ֶ��� , ��ʼʱ�� , ����ʱ�� , ʱ������ 4 �������������ṩ . �� Field Facet ���� ,Date
	 * Facet Ҳ���ԶԶ���ֶν��� Facet. �������ÿ���ֶζ����Ե������ò��� . 2.1 facet.date �ò�����ʾ��Ҫ���� Date
	 * Facet ���ֶ��� , �� facet.field һ�� , �ò������Ա����ö�� , ��ʾ�Զ���ֶν��� Date Facet. 2.2
	 * facet.date.start ��ʼʱ�� , ʱ���һ���ʽΪ �� 1995-12-31T23:59:59Z��, �������ʹ��
	 * ��NOW��,��YEAR��,��MONTH�� �ȵ� , �����ʽ���Բο� org.apache.solr.schema. DateField ��
	 * java doc. 2.3 facet.date.end ����ʱ�� . 2.4 facet.date.gap ʱ���� . ��� start Ϊ
	 * 2009-1-1,end Ϊ 2010-1-1.gap ����Ϊ ��+1MONTH�� ��ʾ��� 1 ���� , ��ô��������ʱ�仮��Ϊ 12
	 * ������� . ע�� ��+�� ��Ϊ�������ַ�����Ӧ���� ��%2B�� ���� . 2.5 facet.date.hardend ȡֵ����Ϊ
	 * true|false, Ĭ��Ϊ false. ����ʾ gap ������ end �����ú��ִ��� . ����˵�� start Ϊ
	 * 2009-1-1,end Ϊ 2009-12-25,gap Ϊ ��+1MONTH��,hardend Ϊ false �Ļ����һ��ʱ���Ϊ
	 * 2009-12-1 �� 2010-1-1;hardend Ϊ true �Ļ����һ��ʱ���Ϊ 2009-12-1 �� 2009-12-25.
	 * 2.6 facet.date.other ȡֵ��ΧΪ before|after|between|none|all, Ĭ��Ϊ none.
	 * before ��� start ֮ǰ��ֵ��ͳ�� . after ��� end ֮���ֵ��ͳ�� . between ��� start �� end
	 * ֮������ֵ��ͳ�� . ��� hardend Ϊ true �Ļ� , ��ô��ֵ���Ǹ���ʱ���ͳ��ֵ�ĺ� . none ��ʾ������� . all ��ʾ
	 * before,after,all ����ͳ�� . ���� : &facet=on &facet.date=date
	 * &facet.date.start=2009-1-1T0:0:0Z &facet.date.end=2010-1-1T0:0:0Z
	 * &facet.date.gap=%2B1MONTH &facet.date.other=all
	 */
	public void FacetFieldQuery() throws Exception {
		solrServer = createSolrServer();
		SolrQuery query = new SolrQuery();// ����һ���µĲ�ѯ
		query.setQuery("jobsName:�����ά��");
		query.setFacet(true);// ����facet=on
		// ������Ϣ��Ϊ��нˮ������ʱ�䣬�����������������飬��˾���ͣ���������
		query.addFacetField(new String[] { "salary", "publishDate",
				"educateBackground", "jobExperience", "companytype", "jobsType" });// ������Ҫfacet���ֶ�
		query.setFacetLimit(10);// ����facet���ص�����
		query.setFacetMissing(false);// ��ͳ��null��ֵ
		query.setFacetMinCount(1);// ���÷��ص�������ÿ�������������Сֵ����������Ϊ1����ͳ��������СΪ1����Ȼ����ʾ

		// query.addFacetQuery("publishDate:[2014-04-11T00:00:00Z TO 2014-04-13T00:00:00Z]");
		QueryResponse response = solrServer.query(query);
		System.out.println("��ѯʱ�䣺" + response.getQTime());
		List<FacetField> facets = response.getFacetFields();// ���ص�facet�б�
		for (FacetField facet : facets) {
			System.out.println(facet.getName());
			System.out.println("----------------");
			List<Count> counts = facet.getValues();
			for (Count count : counts) {
				System.out.println(count.getName() + ":" + count.getCount());
			}
			System.out.println();
		}

	}

	// ʱ��Ƭʹ�÷���
	public void FacetFieldQueryDate() throws Exception {
		solrServer = createSolrServer();
		SolrQuery query = new SolrQuery();// ����һ���µĲ�ѯ
		query.setQuery("jobsName:����");
		query.setFacet(true);// ����facet=on
		query.setFacetLimit(10);// ����facet���ص�����
		query.setFacetMissing(false);// ��ͳ��null��ֵ
		query.setFacetMinCount(1);// ���÷��ص�������ÿ�������������Сֵ����������Ϊ1����ͳ��������СΪ1����Ȼ����ʾ
		query.addFacetField(new String[] { "salary", "educateBackground",
				"jobExperience", "companytype", "jobsType" });// ������Ҫfacet���ֶ�
		// query.addFacetQuery("publishDate:[2014-04-21T00:00:00Z TO 2014-04-23T00:00:00Z]");
		// query.addFacetQuery("publishDate:[2014-04-11T00:00:00Z TO 2014-04-13T00:00:00Z]");
		SimpleDateFormat time0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat time1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time2 = new SimpleDateFormat("HH:mm:ss");
		// return
		// date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"T"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();

		Calendar c = Calendar.getInstance();
		c.setTime(time0.parse(time1.format(c.getTime()) + " 23:59:59"));
		Date date = c.getTime();
		String dateNow = time1.format(date) + "T" + time2.format(date) + "Z";
		c.setTime(time0.parse(time1.format(c.getTime()) + " 23:59:59"));
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		// ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -2);
		date = c.getTime();
		// ǰ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -4);
		date = c.getTime();
		// ǰһ��
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -7);
		date = c.getTime();
		// ǰ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -16);
		date = c.getTime();
		// ǰһ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -30);
		date = c.getTime();
		// ǰ������
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");

		QueryResponse response = solrServer.query(query);
		System.out.println("��ѯʱ�䣺" + response.getQTime());
		List<FacetField> facets = response.getFacetFields();// ���ص�facet�б�
		for (FacetField facet : facets) {
			System.out.println(facet.getName());
			System.out.println("----------------");
			List<Count> counts = facet.getValues();
			for (Count count : counts) {
				System.out.println(count.getName() + ":" + count.getCount());
			}
			System.out.println();
		}
		// ����ʱ�������ȡ����
		Map<String, Integer> maps = response.getFacetQuery();
		for (Entry<String, Integer> entry : maps.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

	}

	// ����ʹ�õĲ�ѯ��ʽ
	// SolrJ�ṩ�Ĳ�ѯ���ܱȽ�ǿ�󣬿��Խ��н���в�ѯ����Χ��ѯ������ȡ�
	// ����һ�·�Χ��ѯ�ĸ�ʽ��[star t TO end]��start��end����Ӧ���ݸ�ʽ��ֵ���ַ�����ʽ����TO�� һ��Ҫ���ִ�д��
	/*
	 * field ��ѯ���ֶ��������� key ��ѯ���ֶ����ƶ�Ӧ��ֵ start ��ѯ����ʼλ�� count һ�β�ѯ���������� sortfield
	 * ��Ҫ������ֶ����� flag ��Ҫ������ֶε�����ʽ���Ϊtrue ���� ���Ϊfalse ���� hightlight �Ƿ���Ҫ������ʾ
	 */
	public QueryResponse searchResult(String[] field, String[] key, int start,
			int count, String[] sortfield, Boolean[] flag, Boolean hightlight)
			throws Exception {
		solrServer = createSolrServer();
		// ��������Ƿ�Ϸ�
		if (null == field || null == key || field.length != key.length) {
			return null;
		}
		

		SolrQuery query = null;
		try {
			// ��ʼ����ѯ����
			query = new SolrQuery(field[0] + ":" + key[0]);
			for (int i = 0; i < field.length; i++) {
				query.addFilterQuery(field[i] + ":" + key[i]);
			}
			// ������ʼλ���뷵�ؽ����
			query.setStart(start);
			query.setRows(count);
			// ��������
			if (!(null == sortfield || null == flag
					|| sortfield.length != flag.length)) {
				for (int i = 0; i < sortfield.length; i++) {
					if (flag[i]) {
						query.addSortField(sortfield[i], SolrQuery.ORDER.asc);
					} else {
						query.addSortField(sortfield[i], SolrQuery.ORDER.desc);
					}
				}
			}
			
			// ���ø���
			if (null != hightlight) {
				query.setHighlight(true); // �����������
				query.addHighlightField("jobsName");// �����ֶ�
				query.setHighlightSimplePre("<font color=\"red\">");// ���
				query.setHighlightSimplePost("</font>");
				query.setHighlightSnippets(1);// �����Ƭ����Ĭ��Ϊ1
				query.setHighlightFragsize(1000);// ÿ����Ƭ����󳤶ȣ�Ĭ��Ϊ100

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		query.setFacet(true);// ����facet=on
		query.setFacetLimit(10);// ����facet���ص�����
		query.setFacetMissing(false);// ��ͳ��null��ֵ
		query.setFacetMinCount(1);// ���÷��ص�������ÿ�������������Сֵ����������Ϊ1����ͳ��������СΪ1����Ȼ����ʾ
		query.addFacetField(new String[] { "salary", "educateBackground",
				"jobExperience", "companytype", "jobsType" });// ������Ҫfacet���ֶ�
		// query.addFacetQuery("publishDate:[2014-04-21T00:00:00Z TO 2014-04-23T00:00:00Z]");
		// query.addFacetQuery("publishDate:[2014-04-11T00:00:00Z TO 2014-04-13T00:00:00Z]");
		SimpleDateFormat time0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat time1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time2 = new SimpleDateFormat("HH:mm:ss");
		// return
		// date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"T"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();

		Calendar c = Calendar.getInstance();
		c.setTime(time0.parse(time1.format(c.getTime()) + " 23:59:59"));
		Date date = c.getTime();
		String dateNow = time1.format(date) + "T" + time2.format(date) + "Z";
		c.setTime(time0.parse(time1.format(c.getTime()) + " 23:59:59"));
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		// ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -2);
		date = c.getTime();
		// ǰ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -4);
		date = c.getTime();
		// ǰһ��
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -7);
		date = c.getTime();
		// ǰ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -16);
		date = c.getTime();
		// ǰһ����
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");
		c.add(Calendar.DATE, -30);
		date = c.getTime();
		// ǰ������
		query.addFacetQuery("publishDate:[" + time1.format(date) + "T"
				+ time2.format(date) + "Z" + " TO " + dateNow + "]");

		QueryResponse rsp = null;
		try {
			rsp = solrServer.query(query);
			System.out.println("�˴β�ѯʱ��qtime :" + rsp.getQTime());
			List<FacetField> facets = rsp.getFacetFields();// ���ص�facet�б�
			for (FacetField facet : facets) {
				System.out.println(facet.getName());
				System.out.println("----------------");
				List<Count> counts = facet.getValues();
				for (Count countitem : counts) {
					System.out.println(countitem.getName() + ":"
							+ countitem.getCount());
				}
				System.out.println();
			}
			// ����ʱ�������ȡ����
			Map<String, Integer> maps = rsp.getFacetQuery();
			for (Entry<String, Integer> entry : maps.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			// ��ȡ���صĽ��
			SolrDocumentList docs = rsp.getResults();			
			for (SolrDocument doc : docs) {
				System.out.println("-----");
				System.out.println(doc.getFieldValue("jobsName"));
				System.out.println(doc.getFieldValue("publishDate"));

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// ���ز�ѯ���
		return rsp;
	}
}
