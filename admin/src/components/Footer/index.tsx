import React from 'react';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';

export default () => (
    <DefaultFooter
        copyright={`${new Date().getFullYear()} 小鱼快游 All Rights Reserved`}
        links={[
          {
            key: 'x',
            title: '小鱼快游',
            href: 'http://x',
            blankTarget: true,
          },
        ]}
    />
);
