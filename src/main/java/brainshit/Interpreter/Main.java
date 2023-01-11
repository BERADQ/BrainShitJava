package brainshit.Interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		//取消了规定输出编码格式的声明，声明将会被错误识别，请勿尝试声明输出编码格式。
		//- 改为真实占位，会被计算长度
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		String code = s.toUpperCase()
				.replaceAll("0", "LXFRSLJYR")
				.replaceAll("/\\*[\\s\\S]*?\\*/", "")
				.replaceAll("[^SLRFJXYOCIABP1-]", "");
		char[] cArray = code.toCharArray();
		byte[] main = new byte[1024];
		List<Byte> ABuffer = new ArrayList<>();
		List<Byte> BBuffer = new ArrayList<>();
		byte temp = 0;
		System.out.println(cArray);
		
		int mainIndex = 0;
		int xMainIndex = 0;
		int xI = 0;
		int i = 0;
		while (i < cArray.length)
		{
			xMainIndex = mainIndex % 1024;
			xMainIndex = (mainIndex < 0) ? 1024 + mainIndex : xMainIndex;
			xI = (i < 0) ? cArray.length + i : i;
			System.out.println(xI + ":" + cArray[xI] + "\\ " + xMainIndex + ":" + main[xMainIndex]);
			switch (cArray[xI])
			{
				case '1' ->
				{//当前Byte的值增加1。
					main[xMainIndex]++;
				}
				case 'S' ->
				{//当前Byte的值减少1。
					main[xMainIndex]--;
				}
				case 'L' ->
				{//将指针向左移动一位。
					mainIndex--;
				}
				case 'R' ->
				{//将指针向右移动一位。
					mainIndex++;
				}
				case 'F' ->
				{//标记这个指令在代码中的的位置到当前Byte中。
					main[xMainIndex] = (byte) xI;
				}
				case 'J' ->
				{//如果当前Byte的下一位不为0，则跳到当前Byte值所对应的指令位置继续执行。需要注意的是，值所指向的指令本身不会被执行。
					if (main[(xMainIndex + 1) % 1024] > (byte) 0) i = main[xMainIndex];
				}
				case 'X' ->
				{//缓存当前Byte的值到唯一的独立Byte中。
					temp = main[xMainIndex];
				}
				case 'Y' ->
				{//读取独立Byte的值写到当前Byte。
					main[xMainIndex] = temp;
				}
				case 'O' ->
				{//将当前Byte的值存进A-Buffer尾部。
					ABuffer.add(main[xMainIndex]);
				}
				case 'C' ->
				{//清空A-Buffer。
					ABuffer.clear();
				}
				case 'I' ->
				{//获取输入的字串，将当前Byte作为index，输入字串到B-Buffer对应位置中。
					String ans = sc.nextLine();
					char[] chars = ans.toCharArray();
					for (int i1 = 0; i1 < chars.length; i1++)
					{
						BBuffer.add((byte) 0);
						BBuffer.set(main[xMainIndex] + i1, (byte) chars[i1]);
					}
					
				}
				case 'A' ->
				{//将当前Byte作为index，读取A-Buffer中对应的值覆盖当前Byte。
					main[xMainIndex] = ABuffer.get(main[xMainIndex]);
				}
				case 'B' ->
				{//将当前Byte作为index，读取B-Buffer中对应的值覆盖当前Byte。
					main[xMainIndex] = BBuffer.get(main[xMainIndex]);
				}
				case 'P' ->
				{//将A-Buffer中的数据一次性输出。
					System.out.println(ABuffer);
					Byte[] tbt = new Byte[ABuffer.size()];
					System.out.println(new String(toPrimitives(ABuffer.toArray(tbt))));
				}
			}
			
			i++;
		}
	}
	
	static byte[] toPrimitives(Byte[] oBytes)
	{
		byte[] bytes = new byte[oBytes.length];
		
		for (int i = 0; i < oBytes.length; i++)
		{
			bytes[i] = oBytes[i];
		}
		
		return bytes;
	}
}
