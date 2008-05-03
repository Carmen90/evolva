package es.ucm.fdi.utils;

public class Ciudades {
	public static final int NUM_CIUDADES = 27;
	public static final int MADRID = 27;

	public static final int[][] DISTANCIAS = 
		{{ 0,  171,  369,  366,  525,  540,  646,  488,  504,  617,  256,  207,  354,  860,  142,  640,  363,  309,  506,  495,  264,  584,  515,  578,  762,  473,  150,  251},  
		{171,    0,  294,  537,  696,  515,  817,  659,  675,  688,  231,  378,  525, 1031,  313,  615,  353,  480,  703,  570,  415,  855,  490,  653,  933,  482,   75,  422},  
		{369,  294,    0,  663,  604,  809,  958,  800,  651,  484,  525,  407,  332, 1172,  511,  909,  166,  621,  516,  830,  228,  896,  802,  899, 1074,  219,  219,  563},  
		{366,  537,  663,    0,  318,  717,  401,  243,  229,  618,  532,  256,  457,  538,  282,  817,  534,  173,  552,  490,  435,  255,  558,  358,  440,  644,  516,  115},  
		{525,  696,  604,  318,    0, 1022,  694,  536,   89,  342,  805,  318,  272,  772,  555, 1122,  438,  459,  251,  798,  376,  496,  866,  676,  674,  436,  675,  401},  
		{540,  515,  809,  717, 1022,    0,  620,  583,  918, 1284,  284,  811,  908, 1118,  562,  100,  868,  563, 1140,  274,  804,  784,  156,  468, 1020,  997,  590,  621},  
		{646,  817,  958,  401,  694,  620,    0,  158,  605, 1058,  607,  585,  795,  644,  562,  720,  829,  396,  939,  322,  730,  359,  464,  152,  546,  939,  796,  395},  
		{488,  659,  800,  243,  536,  583,  158,    0,  447,  900,  524,  427,  637,  535,  404,  683,  671,  238,  781,  359,  572,  201,  427,  115,  437,  781,  638,  237},  
		{504,  675,  651,  229,   89,  918,  605,  447,    0,  369,  701,  324,  319,  683,  451, 1018,  485,  355,  323,  694,  423,  407,  762,  595,  585,  506,  654,  297},  
		{617,  688,  484,  618,  342, 1284, 1058,  900,  369,    0,  873,  464,  263, 1072,  708, 1384,  335,  721,  219, 1060,  367,  796, 1128,  999,  974,  265,  613,  663},  
		{256,  231,  525,  532,  805,  284,  607,  524,  701,  873,    0,  463,  610, 1026,  305,  384,  584,  396,  856,  355,  520,  725,  259,  455,  928,  713,  306,  417},  
		{207,  378,  407,  256,  318,  811,  585,  427,  324,  464,  463,    0,  201,  799,  244,  911,  278,  248,  433,  587,  179,  511,  655,  526,  696,  388,  357,  190},  
		{354,  525,  332,  457,  272,  908,  795,  637,  319,  263,  610,  201,    0,  995,  445, 1008,  166,  458,  232,  797,  104,  733,  865,  736,  897,  187,  444,  400},  
		{860, 1031, 1172,  538,  772, 1118,  644,  535,  683, 1072, 1026,  799,  995,    0,  776, 1218, 1043,  667, 1006,  905,  944,  334,  973,  650,   98, 1153, 1010,  609},  
		{142,  313,  511,  282,  555,  562,  562,  404,  451,  708,  305,  244,  445,  776,    0,  662,  479,  486,  677,  406,  380,  500,  472,  464,  678,  615,  292,  167},  
		{640,  615,  909,  817, 1122,  100,  720,  683, 1018, 1384,  384,  911, 1008, 1218,  662,    0,  968,  663, 1240,  374,  904,  884,  256,  568, 1120, 1097,  690,  721},  
		{363,  353,  166,  534,  438,  868,  829,  671,  485,  335,  584,  278,  166, 1043,  479,  968,    0,  492,  350,  831,   99,  761,  861,  770,  945,  129,  278,  434},  
		{309,  480,  621,  173,  459,  563,  396,  238,  355,  721,  396,  248,  458,  667,  486,  663,  492,    0,  690,  339,  393,  391,  407,  278,  569,  602,  459,   58},  
		{506,  703,  516,  552,  251, 1140,  939,  781,  323,  219,  856,  433,  232, 1006,  677, 1240,  350,  690,    0, 1029,  336,  730, 1097,  968,  908,  313,  628,  632},  
		{495,  570,  830,  490,  798,  274,  322,  359,  694, 1060,  355,  587,  797,  905,  406,  374,  831,  339, 1029,    0,  732,  560,  118,  244,  807,  941,  611,  397},  
		{264,  415,  228,  435,  376,  804,  730,  572,  423,  367,  520,  179,  104,  944,  380,  904,   99,  393,  336,  732,    0,  668,  779,  671,  846,  209,  340,  335},  
		{584,  855,  896,  255,  496,  784,  359,  201,  407,  796,  725,  511,  733,  334,  500,  884,  761,  391,  730,  560,  668,    0,  628,  316,  236,  877,  734,  333},  
		{515,  490,  802,  558,  866,  156,  464,  427,  762, 1128,  259,  655,  865,  973,  472,  256,  861,  407, 1097,  118,  779,  628,    0,  312,  875, 1009,  583,  465},  
		{578,  653,  899,  358,  676,  468,  152,  115,  595,  999,  455,  526,  736,  650,  464,  568,  770,  278,  968,  244,  671,  316,  312,    0,  352,  880,  694,  336},  
		{762,  933, 1074,  440,  674, 1020,  546,  437,  585,  974,  928,  696,  897,   98,  678, 1120,  945,  569,  908,  807,  846,  236,  875,  352,    0, 1055,  912,  511},  
		{473,  482,  219,  644,  436,  997,  939,  781,  506,  265,  713,  388,  187, 1153,  615, 1097,  129,  602,  313,  941,  209,  877, 1009,  880, 1055,    0,  407,  544},  
		{150,   75,  219,  516,  675,  590,  796,  638,  654,  613,  306,  357,  444, 1010,  292,  690,  278,  459,  628,  611,  340,  734,  583,  694,  912,  407,    0,  401},
		{251,  422,  563,  115,  401,  621,  395,  237,  297,  663,  417,  190,  400,  609,  167,  721,  434,   58,  632,  397,  335,  333,  465,  336,  511,  544,  401,    0}};
}
