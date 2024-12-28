package TestPackage;




import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Test;
import BaseClassPackage.SastaBase;
import PagePackage.SastaProgram;
public class SastaTest extends SastaBase 
{
  @Test
  public void testSasta() throws AWTException, InterruptedException, IOException
  {
	  SastaProgram obj = new SastaProgram(driver);
	 
	  obj.SetLoginValues("hanakutty@gmail.com", "hanuman@#");
      obj.ScrollDownClick();
      obj.WindowHandleClick();
      obj.TitleVerification();
      obj.CopyPaste();
      obj.DataDrivenTest();
      obj.dropDownHandling();
      obj.DragAndDrop();
      obj.linkCount();
      obj.PageSource();
      obj.ScreenShot();
      obj.linktext();
  }
}	