package textWriting;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import colors.MapColors;

public class BlockCutting {

	/**
	 * Cut the map in a hashmap of states described by lines (list of line)
	 * @param map The image to be cut
	 * @return
	 */
	public static HashMap<Integer, LinkedList<Line>> enumerateLine(
			BufferedImage map, MapColors mapColors) {
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
						!mapColors.isTrueState(map.getRGB(x, y)))) {				
					if ((map.getRGB(x, y) & 0xffffff) == rgb) {
						lastRGB = x;
					}
					x++;
				}
				// End of map line or of state line (next pixel => lastRGB)
				if (beginLine < lastRGB) {
					storeLines.addLast(new Line(new Point(beginLine, y),
							new Point(lastRGB, y)));
				}
				// Search for storing the sea
				int lastX = x;
				x = lastRGB + 1;
				while (x < lastX &&
						(map.getRGB(x, y) & 0xffffff) != mapColors.getWaterColor()) {
					x++;
				}
				beginLine = x;
				while (x < lastX && !mapColors.isTrueState(map.getRGB(x, y))) {
					if ((map.getRGB(x, y) & 0xffffff) == mapColors.getWaterColor()) {
						lastRGB = x;
					}
					x++;
				}
				// Store sea line
				LinkedList<Line> seaLines = storeStateLines.get(mapColors.getWaterColor());
				if (seaLines == null) {
					// It is the first pixel of sea
					seaLines = new LinkedList<Line>();
					storeStateLines.put(mapColors.getWaterColor(), seaLines);
				}
				if (beginLine < lastRGB) {
					seaLines.addLast(new Line(new Point(beginLine, y),
						new Point(lastRGB, y)));
				}
			}
		}
		return storeStateLines;
	}

	/**
	 * USELESS because now done in enumerateLine
	 * @param map
	 * @param waterColor
	 * @return
	 */
	public static LinkedList<Line> enumerateSeaLine(
			BufferedImage map, int waterColor) {
		LinkedList<Line> seaLines = new LinkedList<Line>();
		for (int y = 0; y < map.getHeight(); y++) {
			int x = 0;
			// Searching a line with sea
			while (x < map.getWidth()) {
				if ((map.getRGB(x, y) & 0xffffff) != waterColor) {
					x++;
				} else {
					// Line with sea found
					int beginLine = x;
					x++;
					// Searching the end of this line
					while (x < map.getWidth() &&
							(map.getRGB(x, y) & 0xffffff) == waterColor) {
						x++;
					}
					// Store this sea line
					seaLines.addLast(new Line(new Point(beginLine, y),
							new Point(x - 1, y)));
				}
			}
		}
		return seaLines;
	}

	/**
	 * Cut the lines of the state into blocks 
	 * @param state List of lines of the State (obtained by enumerateLine)
	 * @return
	 */
	public static LinkedList<PriorityQueue<Line>> cutInBlocks(LinkedList<Line> state) {
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
