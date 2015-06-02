package test.com.ibm.findyourlove.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.service.WatsonPersonalInsights;
import com.ibm.findyourlove.util.FileTool;

/** 
 * @author Yang Zhong(Jack)
 * @version time：Jun 2, 2015 8:56:54 PM 
 * Description:
 * TODO 
 */
public class WatsonPersonalInsightsTest {

	@Test
	public void testWatsonPersonalInsights() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTraitsList() {
		String maleInsightsFolder=FileTool.maleInsightsFolder;
		String insightsFileFullPath=maleInsightsFolder+"1.txt";

		try {
			String socialData = FileTool.getFileContent(new File(insightsFileFullPath));
			WatsonPersonalInsights watsonPersonalInsights = new WatsonPersonalInsights();
			List<Trait> traits = watsonPersonalInsights.getTraitsList(socialData);	
			
			System.out.println(traits.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
