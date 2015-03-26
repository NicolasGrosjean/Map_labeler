package textWriting;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class BlockCutting {

	/**
	 * Cut the map in a hashmap of states described by lines (list of line)
	 * @param map The image to be cut
	 * @return
	 */
	public static HashMap<Integer, LinkedList<Line>> enumerateLine(
			BufferedImage map, int waterColor, int borderColor) {
		HashMap<Integer, LinkedList<Line>> storeStateLines = 
				new HashMap<Integer, LinkedList<Line>>();

		for (int y = 0; y < map.getHeight(); y++) {
			int x = 0;
			while (x < map.getWidth()) {
				int beginLine = x;
				int rgb = map.getRGB(beginLine, y) & 0xffffff;
				LinkedList<Line> storeLines = storeStateLines.get(rgb);
				if (storeLines == null) {
					// It is the first pixel of this State
					storeLines = new LinkedList<Line>();
					storeStateLines.put(rgb, storeLines);
				}
				x++;
				int lastRGB = x - 1; // A Line finish by the state color
				// We search the end of this line
				while (x < map.getWidth() && 
						((map.getRGB(x, y) & 0xffffff) == rgb ||
						(map.getRGB(x, y) & 0xffffff) == waterColor ||
						(map.getRGB(x, y) & 0xffffff) == borderColor)) {				
					if ((map.getRGB(x, y) & 0xffffff) == rgb) {
						lastRGB = x;
					}
					x++;
				}
				// End of map line or of state line (next pixel => lastRGB)
				storeLines.addLast(new Line(new Point(beginLine, y),
						new Point(lastRGB, y)));
			}
		}
		return storeStateLines;
	}

	/**
	 * Cut the blocks of the state
	 * @param state List of lines of the State (obtained by enumerateLine)
	 * @return
	 */
	public static LinkedList<PriorityQueue<Line>> cutBlocks (LinkedList<Line> state) {
		// Create the first block of this State
		Comparator<Line> comparator = new LineComparator();
		PriorityQueue<Line> firstBlock = new PriorityQueue<Line>(10, comparator);
		firstBlock.offer(state.removeFirst());

		// Create the block list and add the first block to it
		LinkedList<PriorityQueue<Line>> blockList = new LinkedList<PriorityQueue<Line>>();
		blockList.addLast(firstBlock);

		// Store lines in blocks
		while (!state.isEmpty()) {
			Line l = state.removeFirst();
			// List the blocks in which l is
			LinkedList<PriorityQueue<Line>> includeBlocks = 
					new LinkedList<PriorityQueue<Line>>();
			for (PriorityQueue<Line> block : blockList) {
				// We must see each line of block to know if l is in block
				PriorityQueue<Line> tmp = new PriorityQueue<Line>(block);
				Line blockLine = tmp.poll();
				boolean isInBlock = true;
				while (l.getBeginLine().getY() != blockLine.getBeginLine().getY() + 1 ||
						l.getBeginLine().getX() > blockLine.getEndLine().getX() ||
						l.getEndLine().getX() < blockLine.getBeginLine().getX()) {
					if (tmp.isEmpty()) {
						isInBlock = false;
						break;
					} else {
						blockLine = tmp.poll();
					}
				}

				if(isInBlock) {
					includeBlocks.addLast(block);
				}
			}
			if (includeBlocks.size() == 0) {
				// l is in no block. So we create a block with l
				PriorityQueue<Line> lBlock = new PriorityQueue<Line>(10, comparator);
				lBlock.offer(l);
				blockList.addLast(lBlock);
			} else if (includeBlocks.size() == 1) {
				// l is in only one block{
				includeBlocks.removeFirst().offer(l);				
			} else {
				// Fusion of the blocks
				PriorityQueue<Line> firstLBlock = includeBlocks.removeFirst();
				for (PriorityQueue<Line> block : includeBlocks) {
					// Remove block for the block list
					blockList.remove(block);
					// Fusion with firstLBlock
					for (Line line : block) {
						firstLBlock.offer(line);
					}
				}
				firstLBlock.offer(l);
			}
		}		
		return blockList;
	}

	public static class LineComparator implements Comparator<Line> {

		@Override
		public int compare(Line l1, Line l2) {
			return l2.getBeginLine().y - l1.getBeginLine().y;
		}
	}
}
