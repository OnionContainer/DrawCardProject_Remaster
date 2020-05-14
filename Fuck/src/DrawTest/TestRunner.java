package DrawTest;

import java.util.LinkedList;


public class TestRunner {//程序主类

	public static void main(String[] args) {
		
		LinkedList<SetType> typeList = new LinkedList<SetType>();
		typeList.add(SetType.FESTIVAL);
		typeList.add(SetType.TWO_OF_BOTH);
		
		
		new TestRunner().implementTest(typeList, 1500, 20);
		
	}
	
	public void implementTest(LinkedList<SetType> typeList, int maxDraw, int drawIncrement) {
		Painter p = new Painter();
		
		LinkedList<MyVec> resultList = new LinkedList<MyVec>();
		
		DrawTest test = new DrawTest(0, typeList);
		
		for (int draw = 0; draw <= maxDraw; draw += drawIncrement) {

			test.maxDraw = draw;
			
			for (int repeat = 0; repeat < 5; repeat += 1) {
				resultList.add(test.test());
			}
			
			
			
			
		}
		
		p.setList(resultList);
	}
}

enum SetType{//up池种类
	ONE_OF_BOTH,//双up抽其中一个
	TWO_OF_BOTH,//双up抽齐
	NORMAL,//单up普通池
	FESTIVAL//单up翻倍池
}



class DrawTest{//测试类

	public int maxDraw = 0;
	public LinkedList<SetType> expList = new LinkedList<SetType>(); 
	private int _currentDraw = 0;
	
	public DrawTest(int maxDraw, LinkedList<SetType> expList) {
		this.maxDraw = maxDraw;
		this.expList = expList;
	}
	
	//一千次世界线测试
	public MyVec test() {
		int totalTest = 1000;
		int totalSuccess = 0;
		
		for (int i = 0; i < totalTest; i += 1) {
			_currentDraw = 0;
			boolean success = true;
			for(SetType type:expList) {
				success = success && onePool(type);
			}
			if (success) {
				totalSuccess += 1;
			}
		}
		
		return new MyVec(maxDraw, ((float)totalSuccess)/((float)totalTest));
	}
	
	//一个池子
	private boolean onePool(SetType type) {
		
		double ratio = 0.007;
		
		if (type == SetType.NORMAL) {
			ratio = 0.007;
		} else if (type == SetType.ONE_OF_BOTH) {
			ratio = 0.0035;
		} else if (type == SetType.FESTIVAL) {
			ratio = 0.014;
		} else if (type == SetType.TWO_OF_BOTH) {
			return specialPool();
		}
		
		int well = 0;
		while (_currentDraw < maxDraw) {
			_currentDraw += 1;
			well += 1;
			if (Math.random() < ratio) {
				return true;
			}
			if (well >= 300) {
				return true;
			}
		}
		return false;
	}
	
	private boolean specialPool() {
		
		boolean[] ups = new boolean[] {false, false};
		int well = 0;
		
		while (_currentDraw < maxDraw) {
			_currentDraw += 1;
			well += 1;
			double result = Math.random();
			
			if (result < 0.0035) {//抽到up角色1
				ups[0] = true;
			} else if (result > 0.0035 && result < 0.007){//抽到up角色2
				ups[1] = true;
			}
			
			if (ups[0] && ups[1]) {//两个都抽到了
				return true;
			}
			
			if (well >= 300 && (ups[0]&&!ups[1] || !ups[0]&&ups[1])) {//已经抽到一个且有一井
				return true;
			}
			
			if (well >= 600) {//已经抽到两井
				return true;
			}
		}
		
		return false;
	}
	
}

