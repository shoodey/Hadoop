# Data Preparation

When browsing the [data we were asked to work with](https://cloudstor.aarnet.edu.au/plus/index.php/s/2DhnLGDdEECo4ys) and after reading the [dataset description](https://www.unsw.adfa.edu.au/unsw-canberra-cyber/cybersecurity/ADFA-NB15-Datasets/) it has seemed apparent that we need to work with **/UNSW-NB15 - CSV Files/a part of training and testing set/UNSW_NB15_training-set.csv** file and rely on ****/UNSW-NB15 - CSV Files/NUSW-NB15_features.csv** file to learn about the different features. We noticed some naming inconsistencies in folder and file names (UNSW vs NUSW) but this can be ignored.

We need to create a [decision tree](https://en.wikipedia.org/wiki/Decision_tree) which requires us to select some features or attribute to work with as we cannot afford using all of them, we also need to understand and know which conditions need to be applied on our attributes or decision tree branches as they cannot be chosen at random.

For this purpose we have decided to use [Weka 3](https://www.google.com/search?q=weka+3&oq=weka+3&aqs=chrome..69i57j69i60j0l2j35i39l2.2518j0j4&sourceid=chrome&ie=UTF-8) a software that does many things, amongst which, explore data sets.

We do not wish to go in detail of the process of using weka, but it has allowed us to define which attributes are most important as well as how many distinct values they have as well as their count.

![alt text](_images/weka.PNG ':size=500%')
<span class="caption">Figure 15. Weka</span>

Overall we got to know that duration, protocol and sbyte are the most relevant attributes and that protocol's most important values to handle are: *udp*, *arp*, *tcp*, *ospf*, *sctp* and *unas*, in addition the important duration windows were (0 | 0.16 | +) and sbyte's (0 | 109 | 128 | 201 | +)

![alt text](_images/weka-attributes.PNG ':size=500%')
<span class="caption">Figure 16. Weka's defined relevant attributes</span>

We have also used Weka to generate the decision tree rules for us, as it impossible to do manually by hand.

![alt text](_images/weka-rules.PNG ':size=500%')
<span class="caption">Figure 17. Weka's generated rules</span>