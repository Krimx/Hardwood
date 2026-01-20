import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
		
		public boolean K_ESCAPE = false, K_LEFT = false, K_RIGHT = false, K_UP = false, K_DOWN = false, K_SPACE = false, K_LSHIFT = false, K_RSHIFT = false, K_TAB = false, K_DELETE = false, K_ENTER;
		public boolean K_w = false, K_a = false, K_s = false, K_d = false, K_e = false, K_q = false, K_r = false, K_t = false, K_y = false, K_u = false, K_i = false, K_o = false, K_p = false, K_f = false, K_g = false, K_h = false, K_j = false, K_k = false, K_l = false, K_z = false, K_x = false, K_c = false, K_v = false, K_b = false, K_n = false, K_m = false;
		public boolean K_1 = false, K_2 = false, K_3 = false, K_4 = false, K_5 = false, K_6 = false, K_7 = false, K_8 = false, K_9 = false, K_0 = false;

		public boolean canSpace = true, canEscape = true, canLeft = true, canRight = true, canUp = true, canDown = true, canTab = true, canLShift = true, canDelete = true, canEnter = true;
		public boolean canq = true, canw = true, cane = true, canr = true, cant = true, cany = true, canu = true, cani = true, cano = true, canp = true, cana = true, cans = true, cand = true, canf = true, cang = true, canh = true, canj = true, cank = true, canl = true, canz = true, canx = true, canc = true, canv = true, canb = true, cann = true, canm = true;
		public boolean can1 = true, can2 = true, can3 = true, can4 = true, can5 = true, can6 = true, can7 = true, can8 = true, can9 = true, can0 = true;
		
		@Override
		public void keyTyped(KeyEvent e) {
			//int key = e.getKeyCode();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_ESCAPE) {K_ESCAPE = true;}
			if (key == KeyEvent.VK_LEFT) {K_LEFT = true;}
			if (key == KeyEvent.VK_RIGHT) {K_RIGHT = true;}
			if (key == KeyEvent.VK_UP) {K_UP = true;}
			if (key == KeyEvent.VK_DOWN) {K_DOWN = true;}
			if (key == KeyEvent.VK_SPACE) {K_SPACE = true;}
			if (key == KeyEvent.VK_SHIFT) {K_LSHIFT = true;}
			if (key == KeyEvent.VK_TAB) {K_TAB = true;}
			if (key == KeyEvent.VK_BACK_SPACE) {K_DELETE = true;}
			if (key == KeyEvent.VK_ENTER) {K_ENTER = true;}
			
			if (key == KeyEvent.VK_W) {K_w = true;}
			if (key == KeyEvent.VK_A) {K_a = true;}
			if (key == KeyEvent.VK_S) {K_s = true;}
			if (key == KeyEvent.VK_D) {K_d = true;}
			if (key == KeyEvent.VK_Q) {K_q = true;}
			if (key == KeyEvent.VK_E) {K_e = true;}
			if (key == KeyEvent.VK_R) {K_r = true;}
			if (key == KeyEvent.VK_T) {K_t = true;}
			if (key == KeyEvent.VK_Y) {K_y = true;}
			if (key == KeyEvent.VK_U) {K_u = true;}
			if (key == KeyEvent.VK_I) {K_i = true;}
			if (key == KeyEvent.VK_O) {K_o = true;}
			if (key == KeyEvent.VK_P) {K_p = true;}
			if (key == KeyEvent.VK_F) {K_f = true;}
			if (key == KeyEvent.VK_G) {K_g = true;}
			if (key == KeyEvent.VK_H) {K_h = true;}
			if (key == KeyEvent.VK_J) {K_j = true;}
			if (key == KeyEvent.VK_K) {K_k = true;}
			if (key == KeyEvent.VK_L) {K_l = true;}
			if (key == KeyEvent.VK_Z) {K_z = true;}
			if (key == KeyEvent.VK_X) {K_x = true;}
			if (key == KeyEvent.VK_C) {K_c = true;}
			if (key == KeyEvent.VK_V) {K_v = true;}
			if (key == KeyEvent.VK_B) {K_b = true;}
			if (key == KeyEvent.VK_N) {K_n = true;}
			if (key == KeyEvent.VK_M) {K_m = true;}
			
			if (key == KeyEvent.VK_1) {K_1 = true;}
			if (key == KeyEvent.VK_2) {K_2 = true;}
			if (key == KeyEvent.VK_3) {K_3 = true;}
			if (key == KeyEvent.VK_4) {K_4 = true;}
			if (key == KeyEvent.VK_5) {K_5 = true;}
			if (key == KeyEvent.VK_6) {K_6 = true;}
			if (key == KeyEvent.VK_7) {K_7 = true;}
			if (key == KeyEvent.VK_8) {K_8 = true;}
			if (key == KeyEvent.VK_9) {K_9 = true;}
			if (key == KeyEvent.VK_0) {K_0 = true;}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_ESCAPE) {
				K_ESCAPE = false;
				canEscape = true;
			}
			if (key == KeyEvent.VK_LEFT) {
				K_LEFT = false;
				canLeft = true;
			}
			if (key == KeyEvent.VK_RIGHT) {
				K_RIGHT = false;
				canRight = true;
			}
			if (key == KeyEvent.VK_UP) {
				K_UP = false;
				canUp = true;
			}
			if (key == KeyEvent.VK_DOWN) {
				K_DOWN = false;
				canDown = true;
			}
			if (key == KeyEvent.VK_SPACE) {
				K_SPACE = false;
				canSpace = true;
			}
			if (key == KeyEvent.VK_SHIFT) {
				K_LSHIFT = false;
				canLShift = true;
			}
			if (key == KeyEvent.VK_TAB) {
				K_TAB = false;
				canTab = true;
			}
			if (key == KeyEvent.VK_BACK_SPACE) {
				K_DELETE = false;
				canDelete = true;
			}
			if (key == KeyEvent.VK_ENTER) {
				K_ENTER = false;
				canEnter = true;
			}
			
			if (key == KeyEvent.VK_W) {
				K_w = false;
				canw = true;
			}
			if (key == KeyEvent.VK_A) {
				K_a = false;
				cana = true;
			}
			if (key == KeyEvent.VK_S) {
				K_s = false;
				cans = true;
			}
			if (key == KeyEvent.VK_D) {
				K_d = false;
				cand = true;
			}
			if (key == KeyEvent.VK_E) {
				K_e = false;
				cane = true;
			}
			if (key == KeyEvent.VK_Q) {
				K_q = false;
				canq = true;
			}
			if (key == KeyEvent.VK_R) {
				K_r = false;
				canr = true;
			}
			if (key == KeyEvent.VK_T) {
				K_t = false;
				cant = true;
			}
			if (key == KeyEvent.VK_Y) {
				K_y = false;
				cany = true;
			}
			if (key == KeyEvent.VK_U) {
				K_u = false;
				canu = true;
			}
			if (key == KeyEvent.VK_I) {
				K_i = false;
				cani = true;
			}
			if (key == KeyEvent.VK_O) {
				K_o = false;
				cano = true;
			}
			if (key == KeyEvent.VK_P) {
				K_p = false;
				canp = true;
			}
			if (key == KeyEvent.VK_F) {
				K_f = false;
				canf = true;
			}
			if (key == KeyEvent.VK_G) {
				K_g = false;
				cang = true;
			}
			if (key == KeyEvent.VK_H) {
				K_h = false;
				canh = true;
			}
			if (key == KeyEvent.VK_J) {
				K_j = false;
				canj = true;
			}
			if (key == KeyEvent.VK_K) {
				K_k = false;
				cank = true;
			}
			if (key == KeyEvent.VK_L) {
				K_l = false;
				canl = true;
			}
			if (key == KeyEvent.VK_Z) {
				K_z = false;
				canz = true;
			}
			if (key == KeyEvent.VK_X) {
				K_x = false;
				canx = true;
			}
			if (key == KeyEvent.VK_C) {
				K_c = false;
				canc = true;
			}
			if (key == KeyEvent.VK_V) {
				K_v = false;
				canv = true;
			}
			if (key == KeyEvent.VK_B) {
				K_b = false;
				canb = true;
			}
			if (key == KeyEvent.VK_N) {
				K_n = false;
				cann = true;
			}
			if (key == KeyEvent.VK_M) {
				K_m = false;
				canm = true;
			}
			
			if (key == KeyEvent.VK_1) {
				K_1 = false;
				can1 = true;
			}
			if (key == KeyEvent.VK_2) {
				K_2 = false;
				can2 = true;
			}
			if (key == KeyEvent.VK_3) {
				K_3 = false;
				can3 = true;
			}
			if (key == KeyEvent.VK_4) {
				K_4 = false;
				can4 = true;
			}
			if (key == KeyEvent.VK_5) {
				K_5 = false;
				can5 = true;
			}
			if (key == KeyEvent.VK_6) {
				K_6 = false;
				can6 = true;
			}
			if (key == KeyEvent.VK_7) {
				K_7 = false;
				can7 = true;
			}
			if (key == KeyEvent.VK_8) {
				K_8 = false;
				can8 = true;
			}
			if (key == KeyEvent.VK_9) {
				K_9 = false;
				can9 = true;
			}
			if (key == KeyEvent.VK_0) {
				K_0 = false;
				can0 = true;
			}
		}
		
		//Normal Accessor methods
		public  boolean ESCAPE() {return K_ESCAPE;}
		public  boolean LEFT() {return K_LEFT;}
		public  boolean RIGHT() {return K_RIGHT;}
		public  boolean UP() {return K_UP;}
		public  boolean DOWN() {return K_DOWN;}
		public  boolean SPACE() {return K_SPACE;}
		public  boolean LSHIFT() {return K_LSHIFT;}
		public  boolean TAB() {return K_TAB;}
		public  boolean DELETE() {return K_DELETE;}
		public  boolean ENTER() {return K_ENTER;}
		
		public  boolean W() {return K_w;}
		public  boolean A() {return K_a;}
		public  boolean S() {return K_s;}
		public  boolean D() {return K_d;}
		public  boolean E() {return K_e;}
		public  boolean Q() {return K_q;}
		public  boolean R() {return K_r;}
		public  boolean T() {return K_t;}
		public  boolean Y() {return K_y;}
		public  boolean U() {return K_u;}
		public  boolean I() {return K_i;}
		public  boolean O() {return K_o;}
		public  boolean P() {return K_p;}
		public  boolean F() {return K_f;}
		public  boolean G() {return K_g;}
		public  boolean H() {return K_h;}
		public  boolean J() {return K_j;}
		public  boolean K() {return K_k;}
		public  boolean L() {return K_l;}
		public  boolean Z() {return K_z;}
		public  boolean X() {return K_x;}
		public  boolean C() {return K_c;}
		public  boolean V() {return K_v;}
		public  boolean B() {return K_b;}
		public  boolean N() {return K_n;}
		public  boolean M() {return K_m;}
		
		public  boolean K1() {return K_1;}
		public  boolean K2() {return K_2;}
		public  boolean K3() {return K_3;}
		public  boolean K4() {return K_4;}
		public  boolean K5() {return K_5;}
		public  boolean K6() {return K_6;}
		public  boolean K7() {return K_7;}
		public  boolean K8() {return K_8;}
		public  boolean K9() {return K_9;}
		public  boolean K0() {return K_0;}
		
		//Typed Accessor methods
		public  boolean ESCAPETYPED() {
			if (K_ESCAPE && canEscape) {
				canEscape = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean LEFTTYPED() {
			if (K_LEFT && canLeft) {
				canLeft = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean RIGHTTYPED() {
			if (K_RIGHT && canRight) {
				canRight = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean UPTYPED() {
			if (K_UP && canUp) {
				canUp = false;
				return true;
			}
			else {
				return false;
			}
		}
		public boolean DOWNTYPED() {
			if (K_DOWN && canDown) {
				canDown = false;
				return true;
			}
			else {
				return false;
			}
		}
		public boolean SPACETYPED() {
			if (K_SPACE && canSpace) {
				canSpace = false;
				return true;
			}
			else {
				return false;
			}
		}
		public boolean LSHIFTTYPED() {
			if (K_LSHIFT && canLShift) {
				canLShift = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean TABTYPED() {
			if (K_TAB && canTab) {
				canTab = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean DELETETYPED() {
			if (K_DELETE && canDelete) {
				canDelete = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean ENTERTYPED() {
			if (K_ENTER && canEnter) {
				canEnter = false;
				return true;
			}
			else {
				return false;
			}
		}
		
		public  boolean WTYPED() {
			if (K_w && canw) {
				canw = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean ATYPED() {
			if (K_a && cana) {
				cana = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean STYPED() {
			if (K_s && cans) {
				cans = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean DTYPED() {
			if (K_d && cand) {
				cand = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean ETYPED() {
			if (K_e && cane) {
				cane = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean QTYPED() {
			if (K_q && canq) {
				canq = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean RTYPED() {
			if (K_r && canr) {
				canr = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean TTYPED() {
			if (K_t && cant) {
				cant = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean YTYPED() {
			if (K_y && cany) {
				cany = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean UTYPED() {
			if (K_u && canu) {
				canu = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean ITYPED() {
			if (K_i && cani) {
				cani = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean OTYPED() {
			if (K_o && cano) {
				cano = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean PTYPED() {
			if (K_p && canp) {
				canp = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean FTYPED() {
			if (K_f && canf) {
				canf = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean GTYPED() {
			if (K_g && cang) {
				cang = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean HTYPED() {
			if (K_h && canh) {
				canh = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean JTYPED() {
			if (K_j && canj) {
				canj = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean KTYPED() {
			if (K_k && cank) {
				cank = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean LTYPED() {
			if (K_l && canl) {
				canl = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean ZTYPED() {
			if (K_z && canz) {
				canz = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean XTYPED() {
			if (K_x && canx) {
				canx = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean CTYPED() {
			if (K_c && canc) {
				canc = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean VTYPED() {
			if (K_v && canv) {
				canv = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean BTYPED() {
			if (K_b && canb) {
				canb = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean NTYPED() {
			if (K_n && cann) {
				cann = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean MTYPED() {
			if (K_m && canm) {
				canm = false;
				return true;
			}
			else {
				return false;
			}
		}

		public  boolean K1TYPED() {
			if (K_1 && can1) {
				can1 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K2TYPED() {
			if (K_2 && can2) {
				can2 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K3TYPED() {
			if (K_3 && can3) {
				can3 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K4TYPED() {
			if (K_4 && can4) {
				can4 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K5TYPED() {
			if (K_5 && can5) {
				can5 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K6TYPED() {
			if (K_6 && can6) {
				can6 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K7TYPED() {
			if (K_7 && can7) {
				can7 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K8TYPED() {
			if (K_8 && can8) {
				can8 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K9TYPED() {
			if (K_9 && can9) {
				can9 = false;
				return true;
			}
			else {
				return false;
			}
		}
		public  boolean K0TYPED() {
			if (K_0 && can0) {
				can0 = false;
				return true;
			}
			else {
				return false;
			}
		}
		
		//Axis methods (similar to Unity's system)
		public boolean isUp() {
			if (K_UP || K_w) return true;
			else return false;
		}
		public boolean isDown() {
			if (K_DOWN || K_s) return true;
			else return false;
		}
		public boolean isLeft() {
			if (K_LEFT || K_a) return true;
			else return false;
		}
		public boolean isRight() {
			if (K_RIGHT || K_d) return true;
			else return false;
		}
		
		//Mutator methods
		public void setEscape(boolean in) {K_ESCAPE = in;}
		public void setSpace(boolean in) {K_SPACE = in;}
		public void setA(boolean in) {K_a = in;}
		public void setD(boolean in) {K_d = in;}
		
		public void reInitEnter() {canEnter = true;}
		
	}